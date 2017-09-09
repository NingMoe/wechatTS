package com.mcu32.firstq.wechat.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class CopyObject {
	private static int ALL=0;
	private static int NOTNULL=1;
	private static int NOTEMPTY=2;
	
	private static Object convertBean2BeanWhithRequirement(Object from, Object to,int requirement) {
        try {   
            BeanInfo beanInfo = Introspector.getBeanInfo(to.getClass());   
            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();   
  
            for (PropertyDescriptor p : ps) {   
               Method getMethod = p.getReadMethod();   
               Method setMethod = p.getWriteMethod();
               if (getMethod != null && setMethod != null) {   
                   try {   
                      Object result = getMethod.invoke(from);
                      if(NOTNULL==requirement){
                    	  if(null!=result)
                    		  setMethod.invoke(to, result);   
                      }else if(NOTEMPTY==requirement){
                    	  if(null!=result && !"".equals(result))
                    		  setMethod.invoke(to, result); 
                      }else{
                    	  setMethod.invoke(to, result); 
                      }
                   } catch (Exception e) {   
                      // 如果from没有此属性的get方法，跳过   
                      continue;   
                   }   
               }   
            }   
        } catch (Exception e) {   
           e.printStackTrace();   
        }   
        return to;   
    }
	
	public static Object convertBean2Bean(Object from, Object to) {
        return convertBean2BeanWhithRequirement(from, to,ALL);
    }
	
	public static Object convertBeanNotNull2Bean(Object from, Object to) {
		return convertBean2BeanWhithRequirement(from, to,NOTNULL);
    }
	
	public static Object convertEachBeanNotNull(Object from, Object to) {
		to=convertBean2BeanWhithRequirement(from, to,NOTNULL);
		return convertBean2BeanWhithRequirement(to, from,NOTNULL);
    }
	
	public static Object convertBeanNotEmpty2Bean(Object from, Object to) {
		return convertBean2BeanWhithRequirement(from, to,NOTEMPTY);
    }
  
    /**  
     * 不支持to继承(to是其他bean的子类)  
     */   
   public static Object coverBean2Bean(Object from, Object to) {   
        Class<? extends Object> fClass = from.getClass();   
        Class<? extends Object> tClass = to.getClass();   
        // 拿to所有属性（如果有继承，父类属性拿不到）   
        Field[] cFields = tClass.getDeclaredFields();   
        try {   
            for (Field field : cFields) {   
               String cKey = field.getName();   
               // 确定第一个字母大写   
               cKey = cKey.substring(0, 1).toUpperCase() + cKey.substring(1);   
  
               Method fMethod;   
               Object fValue;   
               try {   
                    fMethod = fClass.getMethod("get" + cKey);// public方法   
                    fValue = fMethod.invoke(from);// 取getfKey的值   
               } catch (Exception e) {   
                 // 如果from没有此属性的get方法，跳过   
                 continue;   
               }   
  
                try {   
                    // 用fMethod.getReturnType()，而不用fValue.getClass()   
                    // 为了保证get方法时，参数类型是基本类型而传入对象时会找不到方法   
                    Method cMethod = tClass.getMethod("set" + cKey, fMethod.getReturnType());   
                    cMethod.invoke(to, fValue);   
                } catch (Exception e) {   
                    // 如果to没有此属性set方法，跳过   
                    continue;   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
        return to;   
    }   
  
  
   static final String spare = "======================================================\r\n";   
  
   /**      * 打印bean信息  
     */   
   public static void printInvoke(Object object) {   
      Method[] ms = object.getClass().getMethods();   
      String str = spare;   
      str += "start log object: " + object.getClass().getSimpleName() + "\r\n";   
      str += spare;   
      System.out.print(str);   
  
      for (int i = 0; i < ms.length; i++) {   
         if (ms[i].getName().indexOf("get") != -1   
             && !ms[i].getName().equals("getClass")) {   
             try {   
                 System.out.println(ms[i].getName() + " = "   
                 + ms[i].invoke(object));   
             } catch (Exception e) {   
                 e.printStackTrace();   
             }   
         }   
      }   
  
     System.out.println(spare);   
   }   

}
