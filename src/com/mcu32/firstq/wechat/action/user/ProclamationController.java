package com.mcu32.firstq.wechat.action.user;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.ProclamationBO;
import com.mcu32.firstq.common.bean.bo.SysFile;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.common.util.LogUtil;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.ConvertFileToHtml;
import com.mcu32.firstq.wechat.util.ToolUtil;

@Controller
@RequestMapping(value = "/proclamation")
public class ProclamationController extends BaseController {
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo userInfo=getSessionUser(session);
		String groupName=request.getParameter("groupName");
		
		if (StringUtils.isNotEmpty(groupName)) {
			try {
				groupName=URLDecoder.decode(groupName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LogUtil.info("从微信群过来的群名 -------->" +groupName);
		model.addAttribute("groupName", groupName);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("fillTime", new Date());
		return "proclamation/fillProclamation";
	}
	
	@RequestMapping(value = "/addProclamation", method = RequestMethod.POST)
	public String addQuestion(ProclamationBO proclamationBO, HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo userInfo=getSessionUser(session);
		
		boolean saveSuccess=false;
		if(StringUtils.isEmpty(proclamationBO.getProclamationId())){
			String uid=getUUID();
			proclamationBO.setProclamationId(uid);
			proclamationBO.setIsTop("0");
			saveSuccess=FirstQInterfaces.getIProclamationService().insert(proclamationBO);
		}else{
			proclamationBO.setLastOperator(userInfo.getUserName());
			proclamationBO.setLastOperatorId(userInfo.getUserId());
			proclamationBO.setOperatTime(new Date());
			saveSuccess=FirstQInterfaces.getIProclamationService().updateByPrimaryKey(proclamationBO);
		}
		if(saveSuccess){
			Map<String,String> rmap=getRealMapByReqeustMap(request);
			SysFile sysFile=new SysFile();
			sysFile.setRelationId(proclamationBO.getProclamationId());
			sysFile.setRelationTable("PROCLAMATION");
			sysFile.setUploadUser(userInfo.getUserName());
			sysFile.setUploadUserId(userInfo.getUserId());
			sysFile.setUploadTime(new Date());
			FirstQInterfaces.getSysFileService().addSysFiles(rmap,sysFile,getFileDocTemp());
		}
		return "";
	}
	
	@RequestMapping(value = "/getProclamationList")
	public String getQuestionList(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String groupName=request.getParameter("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			try {
				groupName=URLDecoder.decode(groupName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("groupName", groupName);
		List<ProclamationBO> list=FirstQInterfaces.getIProclamationService().selectByBaseConditions(map);
		model.addAttribute("proclamationList", list);
		return "proclamation/proclamationList";
	}
	
	@RequestMapping(value = "/toDetail")
	public String toDetail(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String proclamationId=request.getParameter("proclamationId");
		ProclamationBO proclamationBO=FirstQInterfaces.getIProclamationService().selectByPrimaryKey(proclamationId);
		Map<String,String> map=new HashMap<String,String>();
		map.put("relationId", proclamationId);
		map.put("orderStr", "UPLOAD_TIME desc");
		List<SysFile> sysFileList=FirstQInterfaces.getSysFileService().selectByBaseConditions(map);
		model.addAttribute("proclamationBO", proclamationBO);
		model.addAttribute("sysFileList", sysFileList);
		return "proclamation/detail";
	}
	@RequestMapping(value = "/updateProclamation")
	public String updateQuestion(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String proclamationId=request.getParameter("proclamationId");
		ProclamationBO proclamationBO=FirstQInterfaces.getIProclamationService().selectByPrimaryKey(proclamationId);
		String detail=proclamationBO.getProclamationDetail().replace("<br>", "\n");
		Map<String,String> map=new HashMap<String,String>();
		map.put("relationId", proclamationId);
		map.put("orderStr", "UPLOAD_TIME desc");
		List<SysFile> sysFileList=FirstQInterfaces.getSysFileService().selectByBaseConditions(map);
		proclamationBO.setProclamationDetail(detail);
		model.addAttribute("proclamationBO", proclamationBO);
		model.addAttribute("sysFileList", sysFileList);
		return "proclamation/fillProclamation";
	}
	@RequestMapping(value = "/deleteProclamation")
	public String deleteQuestion(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String proclamationId=request.getParameter("proclamationId");
		boolean flag=FirstQInterfaces.getIProclamationService().deleteByPrimaryKey(proclamationId);
		String groupName=request.getParameter("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			try {
				groupName=URLDecoder.decode(groupName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("groupName", groupName);
		List<ProclamationBO> list=FirstQInterfaces.getIProclamationService().selectByBaseConditions(map);
		model.addAttribute("proclamationList", list);
		return "proclamation/proclamationList";
	}
	@RequestMapping(value = "/updateTop")
	public String updateTop(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String proclamationId=request.getParameter("proclamationId");
		ProclamationBO proclamationBO=new ProclamationBO();
		proclamationBO.setProclamationId(proclamationId);
		String fg=request.getParameter("flag");
		boolean flg=Boolean.parseBoolean(fg);
		if(flg){
			proclamationBO.setIsTop("1");
		}else{
			proclamationBO.setIsTop("0");
		}
		boolean flag=FirstQInterfaces.getIProclamationService().updateByPrimaryKey(proclamationBO);
		return "";
	}
	@RequestMapping(value = "/getListTopThree")
	public String getListTopThree(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String groupName=request.getParameter("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			try {
				groupName=URLDecoder.decode(groupName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("groupName", groupName);
		List<ProclamationBO> list=FirstQInterfaces.getIProclamationService().selectListTopThree(map);
		model.addAttribute("proclamationList", list);
		return "";
	}
	@RequestMapping(value = "/deleteFile")
	public String deleteFile(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String fileId=request.getParameter("fileId");
		boolean flag=FirstQInterfaces.getSysFileService().deleteFile(fileId);
		model.addAttribute("succ", flag);
		return "";
	}
	//@RequestMapping("/export")  
    public ResponseEntity<byte[]> export(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	String fileId=request.getParameter("fileId");
		SysFile sysFile=FirstQInterfaces.getSysFileService().selectByPrimaryKey(fileId);
		String absolutePath=sysFile.getAbsolutePath();
		//absolutePath="E:\\apache-tomcat-7.0.59\\webapps\\wechatTS\\docTemp\\20161026152257336695.xls";
		File file = new File(absolutePath);  
        // 取得文件名。  
        String filename = new String(sysFile.getFileName().getBytes("UTF-8"),"iso-8859-1"); 
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
        headers.setContentDispositionFormData("attachment", filename);    
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                              headers, HttpStatus.CREATED);    
    }
    @RequestMapping("/export")
    public String getFileFromOa(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model){ 
        String userAgent=request.getHeader("User-Agent");//里面包含了设备类型  
        System.out.println(userAgent);
        if(-1!=userAgent.indexOf("iPhone")){  
            //-----------------//  
            //此方法需要浏览器自己能够打开，ios可以但是微信andriod版内置浏览器不支持  
            //-----------------//  
            //如果是苹果手机  
            //获得文件地址 
        	String fileId=request.getParameter("fileId");
   		 SysFile sysFile=FirstQInterfaces.getSysFileService().selectByPrimaryKey(fileId);
   		 String fileUrl=sysFile.getAbsolutePath();
           fileUrl.replaceAll("%2B", "\\+");//转换加号  
           //String strURL = MessageUtil.oaUrl+fileUrl;  
           String strURL=ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/docTemp/"+sysFile.getFileStorageName();
             String fileType=strURL.substring(strURL.lastIndexOf(".")+1,strURL.length());  
            //获得图片的数据流  
            try {  
                URL oaUrl = new URL(strURL);  
                HttpURLConnection httpConn = (HttpURLConnection) oaUrl.openConnection();  
                InputStream in = httpConn.getInputStream();  
                //获取输出流 
                request.setCharacterEncoding("UTF-8");  
                response.setCharacterEncoding("UTF-8");  
                String name=fileUrl.substring(fileUrl.lastIndexOf("/")+1, fileUrl.length());  
      
                response.setHeader("Content-Disposition",    
                                       "attachment;filename=" +    
                                               new String( (name ).getBytes("UTF-8"),    
                                                        "iso-8859-1"));  
                if("doc".equals(fileType)||"docx".equals(fileType)){  
                    response.setContentType("application/msword");  
                }else if("xls".equals(fileType)||"xlsx".equals(fileType)){  
                    response.setContentType("application/msexcel");   
                }else{  
                    response.setContentType("application/"+fileType);  
                }  
                OutputStream out = response.getOutputStream();  
                //输出图片信息  
                byte[] bytes = new byte[1024];    
                int cnt=0;    
                while ((cnt=in.read(bytes,0,bytes.length)) != -1) {    
                    out.write(bytes, 0, cnt);    
                }    
                out.flush();  
                out.close();  
                in.close();  
      
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return null;  
        }else{  
            //如果非苹果手机，自己处理文档  
              
            //获得文件地址  
        	String fileId=request.getParameter("fileId");
    		 SysFile sysFile=FirstQInterfaces.getSysFileService().selectByPrimaryKey(fileId);
    		 String fileUrl=sysFile.getAbsolutePath();
            fileUrl.replaceAll("%2B", "\\+");//转换加号  
            //String strURL = MessageUtil.oaUrl+fileUrl;  
            String strURL=ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/docTemp/"+sysFile.getFileStorageName();
            //在本地存放OA文件，然后转换成html，再对文档中的图片路径进行修改，最后输出到页面  
            try {  
                URL oaUrl = new URL(strURL);  
                HttpURLConnection httpConn = (HttpURLConnection) oaUrl.openConnection();  
                InputStream in = httpConn.getInputStream();  
                //获取输出流  
                request.setCharacterEncoding("UTF-8");  
                response.setCharacterEncoding("UTF-8");  
                String name=sysFile.getFileName();
                //首先判断本地是否存在  
                String path=request.getRealPath("");
                path=path.substring(0, path.lastIndexOf("\\")+1);  
                File htmlFile=new File(path +"OaFileToHtml\\"+name+".html");  
                if(!htmlFile.exists()){  
                    //判断文件夹是否存在，创建文件夹  
                    String oaFilePath=path + "OaFile";//存放OA文档的文件夹路径;  
                    File oaFiles=new File(oaFilePath);  
                    if(!oaFiles.exists()){  
                        //如果文件夹不存在创建文件夹  
                        oaFiles.mkdirs();  
                    }  
                    //将OA消息存入本地  
                    File oafile=new File(oaFiles+ File.separator +name);  
                    OutputStream out = new FileOutputStream(oafile);  
                    //输出图片信息  
                    byte[] bytes = new byte[1024];    
                    int cnt=0;    
                    while ((cnt=in.read(bytes,0,bytes.length)) != -1) {    
                        out.write(bytes, 0, cnt);    
                    }    
                    out.flush();  
                    out.close();  
                    in.close();  
                    //转换成html  
                    String htmlFilePath =path + "OaFileToHtml";//OA文件转成html的位置
                    System.out.println(oafile);
                    //oafile=new File("E:\\apache-tomcat-7.0.59\\webapps\\wechatTS\\docTemp\\20161026152257336695.xls");
                    String htmlcontext=ConvertFileToHtml.toHtmlString(oafile, htmlFilePath);  
                    model.addAttribute("htmlcontext", htmlcontext);
                }else{  
                    //已经存在转换成功的文档  
                    StringBuffer htmlSb = new StringBuffer();  
                    try {  
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile),Charset.forName("gb2312")));  
                        while (br.ready()) {  
                        	String utfStr=br.readLine();
                        	
                        	if(StringUtils.isNotEmpty(utfStr)&& utfStr.indexOf("gb2312")>0){
                        		utfStr=utfStr.replace("gb2312", "UTF-8");
                        	}
                        	System.out.println(utfStr);
                            htmlSb.append(utfStr);   
                        }  
                        br.close();  
                    } catch (FileNotFoundException e) {  
                        e.printStackTrace();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                    // HTML文件字符串  
                    String htmlStr = htmlSb.toString();  
                    System.out.println("htmlStr=" + htmlStr);  
                    // 返回经过清洁的html文本  
                   /* request.setAttribute("htmlcontext", ConvertFileToHtml.clearFormat(htmlStr, ""));  */
                    model.addAttribute("htmlcontext", ConvertFileToHtml.clearFormat(htmlStr, ""));
                }  
                  
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return "proclamation/lookfile";  
        }  
          
    }
}
