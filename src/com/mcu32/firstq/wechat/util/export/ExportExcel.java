package com.mcu32.firstq.wechat.util.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;


/**
 */
public class ExportExcel{
	public static String ROWFOREGROUNDCOLOR="RowForegroundColor";
	
	public static void exportExcel(String sourceFilePath,String targetFilePath, Map<String,Object> map) {
		try {
			FileOutputStream fileOut = new FileOutputStream(targetFilePath);
			exportExcel(sourceFilePath,fileOut,map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public static void exportExcel(String sourceFilePath,FileOutputStream fileOut, Map<String,Object> map){
//		String path=ExportExcel.class.getResource("template/").getPath();
//		File file =new File(path+sourceFilePath);
		
		try {
			File file = new ClassPathResource("/template/"+sourceFilePath).getFile();
			String extension = sourceFilePath.lastIndexOf(".") == -1 ? "" : sourceFilePath.substring(sourceFilePath.lastIndexOf(".") + 1);
			if ("xls".equals(extension)) {
				read2003Excel(file,fileOut,map);
				fileOut.close();
			} else if ("xlsx".equals(extension)) {
				read2007Excel(file,fileOut,map);
				fileOut.close();
			} else {
				fileOut.close();
				throw new IOException("不支持的文件类型");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void read2003Excel(File file,OutputStream fileOut,Map<String,Object> datamap) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(0);
		//Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null){
				continue;
			}else{
				counter++;
			}
			
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				
				if(cell.getStringCellValue().startsWith("#")){
					String cellValued=getCellValue(cell).trim().replaceFirst("#", "");
					String cellValue=(String)datamap.get(cellValued);
					setCellValue(cell,cellValue);
				}
				
				if(XSSFCell.CELL_TYPE_STRING==cell.getCellType() && cell.getStringCellValue().startsWith("<list")){
					String listInfo=cell.getStringCellValue().replaceAll(">", "");
					String listName=listInfo.split(" ")[1];
					@SuppressWarnings("unchecked")
					List<Object> data= (List<Object>) datamap.get(listName);
					if(null==data){
						hwb.close();
						return;
					}
					//得到列表
					List<String> linkslist=new LinkedList<String>();
					HSSFRow rows = sheet.getRow(i+1);
					for(Cell cells : rows){
						linkslist.add(getCellValue(cells).trim().replaceFirst("#", ""));
					}
					
					sheet.removeRow(sheet.getRow(i));
					sheet.removeRow(sheet.getRow(i+1));
					sheet.removeRow(sheet.getRow(i+2));
					
					for(int k=0;k<data.size();k++){
						HSSFRow rows1 = sheet.createRow(i);
						i++;
						counter++;
						
						Object rowDate = data.get(k);
						if(rowDate instanceof Map){
							@SuppressWarnings("unchecked")
							Map<String,Object> rowDates=(Map<String,Object>)rowDate;
							for(int z=0;z<linkslist.size();z++){
								Object cellVal=rowDates.get(linkslist.get(z));
								if(null!=cellVal && !"".equals(cellVal)){
									setCellValue(rows1.createCell(z),cellVal);
								}
							}
						}
					}
					break;
				}
			}
			//System.out.println("after i: "+i+" counter: "+counter);
		}
		hwb.write(fileOut);
		hwb.close();
		return;
	}

	/**
	 * 读取Office 2007 excel
	 * */
	@SuppressWarnings("unchecked")
	private static void read2007Excel(File file,OutputStream fileOut,Map<String,Object> datamap) throws Exception {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		
		// 读取第一张表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		//Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		
		for (int i = sheet.getFirstRowNum(); counter < sheet .getLastRowNum()+1&&i<=counter; ) {
			//System.out.println("before i: "+i+" counter: "+counter+" pnor: "+sheet.getLastRowNum());
			row = sheet.getRow(i);
			/*if (row == null){
				System.out.println(row);
				continue;
			}*/
			//System.out.println(row);
			if (row != null)
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if(null!=cell){
					
				
				if(cell.getStringCellValue().startsWith("#")){
					String cellValued=getCellValue(cell).trim().replaceFirst("#", "");
					String cellValue=(String)datamap.get(cellValued);
					Cell c=setCellValue(cell,cellValue);
					
					Object cellBackGroundColor = datamap.get(cellValued+"_cellBackGroundColor");
					if(null!= cellBackGroundColor && cellBackGroundColor instanceof Short){
						XSSFCellStyle style=xwb.createCellStyle();
						style.setFillForegroundColor((Short)cellBackGroundColor);
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);
						c.setCellStyle(style);
					}
				}
			
				
				if(XSSFCell.CELL_TYPE_STRING==cell.getCellType() && cell.getStringCellValue().startsWith("<list")){
					String listInfo=cell.getStringCellValue().replaceAll(">", "");
					String listName=listInfo.split(" ")[1];
					List<Object> data= (List<Object>) datamap.get(listName);//传过来的数据列表
					if(null==data){
						xwb.close();
						return;
					}
					
					List<String> linkslist=new LinkedList<String>();//得到模版中设置的字段名称
					XSSFRow rows = sheet.getRow(i+1);
					for(Cell cells : rows){
						linkslist.add(getCellValue(cells).trim().replaceFirst("#", ""));
					}
					//sheet.shiftRows(i+2,sheet.getLastRowNum(),-3,true,false);
					sheet.removeRow(sheet.getRow(i));
					sheet.removeRow(sheet.getRow(i+1));
					sheet.removeRow(sheet.getRow(i+2));
					
					for(int k=0;k<data.size();k++){
						//XSSFRow rows1 = sheet.createRow(i);
						
						sheet.shiftRows(i, sheet.getLastRowNum(), 1,true,false);
						XSSFRow rows1=sheet.createRow(i);
						
						i++;
						counter++;
						/*if(k<data.size()-1){
							cp=" counter++ ";
						}
						System.out.println("i++ and "+cp+" i="+i+" counter="+counter);*/
						Object rowDate = data.get(k);//传过来的一条list 中的数据
						if(rowDate instanceof Map){
							
							Map<String,Object> rowDates=(Map<String,Object>)rowDate;
							for(int z=0;z<linkslist.size();z++){//每一次循环向表格里面插入一条数据
								Object cellVal=rowDates.get(linkslist.get(z));
								if(null!=cellVal && !"".equals(cellVal)){
									Cell setedCell=setCellValue(rows1.createCell(z),cellVal);
									
									/*如果设置了行的北京颜色，给行的所有单元格加颜色*/
									Object rowForegroundColorIndex=rowDates.get(ROWFOREGROUNDCOLOR);
									if(null!=rowForegroundColorIndex && rowForegroundColorIndex instanceof Short){
										XSSFCellStyle style=xwb.createCellStyle();
										style.setFillForegroundColor((Short)rowForegroundColorIndex);
										style.setFillPattern(CellStyle.SOLID_FOREGROUND);
										setedCell.setCellStyle(style);
									}
								}
							}
							
						}
					}
					break;
				}
				}
			}
			counter++;
			i++;
			//System.out.println("after i++ i=: "+i+" counter ++ counter: "+counter);
			//System.out.println((counter < sheet .getLastRowNum()+1)+"&&"+(i<=counter));
		}
		System.out.println("excel create success");
		xwb.write(fileOut);
		xwb.close();
		return;
	}
	private static Cell setCellValue(Cell cell,Object o){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		
		if(o instanceof String){
			cell.setCellValue(String.valueOf(o));
		}else if(o instanceof Integer){
			cell.setCellValue((Integer)o);
		}else if(o instanceof Double){
			cell.setCellValue((Double)o);
		}else if(o instanceof Date){
			cell.setCellValue(sdf.format(o));
		}else if(o instanceof Long){
			cell.setCellValue((Long)o);
		}
		return cell;
	}
	private static String getCellValue(Cell cell){
		DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
		
		switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				//System.out.println(i + "行" + j + " 列 is String type");
				return cell.getStringCellValue();
			case XSSFCell.CELL_TYPE_NUMERIC:
				//System.out.println(i + "行" + j + " 列 is Number type ; DateFormt:" + cell.getCellStyle().getDataFormatString());
				if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					return df.format(cell.getNumericCellValue());
				} else if ("General".equals(cell.getCellStyle() .getDataFormatString())) {
					return nf.format(cell.getNumericCellValue());
				} else {
					return sdf.format(HSSFDateUtil.getJavaDate(cell .getNumericCellValue()));
				}
			case XSSFCell.CELL_TYPE_BOOLEAN:
				//System.out.println(i + "行" + j + " 列 is Boolean type");
				return String.valueOf(cell.getBooleanCellValue());
			case XSSFCell.CELL_TYPE_BLANK:
				//System.out.println(i + "行" + j + " 列 is Blank type");
				return "";
			default:
				//System.out.println(i + "行" + j + " 列 is default type");
				return cell.toString();
		}
	}
	public static void putCellWhithBackgroundColor(Map<String, Object> dataMap, String key, String value,short index) {
		dataMap.put(key, value);
		dataMap.put(key+"_cellBackGroundColor", index);
	}  
	 /** 
     * @功能：手工构建一个简单格式的Excel 
     */  
  
    public static void main(String[] args) throws Exception  {
    	Map<String,Object> map=new HashMap<String,Object>();
    	
    	List<Map<String,Object>> date=new ArrayList<Map<String,Object>>();
    	for(int i=0;i<10;i++){
    		Map<String,Object> m=new HashMap<String,Object>();
    		
    		m.put("no", "c0");
    		m.put("mobileOperator", "c1");
    		m.put("province", "c2");
    		m.put("city", "c3");
    		m.put("stationName", "c2");
    		m.put("stationNo", 1);
    		
    		m.put("mobileOperator", "c0");
    		m.put("batteryBrand", "c1");
    		m.put("startdate", "c2");
    		m.put("loadCurrent", "c3");
    		m.put("floatingChargeVoltage", "c2");
    		m.put("yhType", 1);

    		m.put("groupNum", 1);
    		m.put("singleCapacity", 1);
    		m.put("voltageAndBatteryNumber", 1);
    		m.put("yhGroupNumber", 1);
    		m.put("yhBatteryNumber", 1);

    		m.put("renovationPlan", 1);
    		m.put("renovationgGroupNumber", 1);
    		m.put("renovationgCapacity", 1);
    		
    		m.put("singleVoltage", 1);
    		m.put("replaceBatteryNumber", 1);
    		m.put("stationBudget", 1);
    		m.put("remark", 1);
    		
    		
    		date.add(m);
    	}
    	
    	date.get(5).put(ROWFOREGROUNDCOLOR, IndexedColors.YELLOW.getIndex());
    	
    	map.put("title", "test");
    	map.put("datalist", date);
    	
    	ExportExcel.putCellWhithBackgroundColor(map,"explain","黄色表示上周填报数据",IndexedColors.YELLOW.getIndex());
    	
    	FileOutputStream fileOut = new FileOutputStream("D:\\batteryt.xlsx");
    	ExportExcel.exportExcel("battery.xlsx",fileOut,map);
    }
}