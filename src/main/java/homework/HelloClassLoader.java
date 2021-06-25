package homework;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws IOException {
        //文件路径
        String classPath = "D:\\test_folder\\Hello.xlass";
        //类名
        String className = "Hello";
        //需要执行的方法名
        String methodName = "hello";
        try {
            HelloClassLoader helloClazz = new HelloClassLoader();
            Class<?> helloClass = helloClazz.findClass(className, classPath);
            Method declaredMethod = helloClass.getDeclaredMethod(methodName);
            declaredMethod.invoke(helloClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected  Class<?> findClass(String name,String path) throws IOException {
        FileInputStream fileInputStream = null;
        byte[] byteArr;
        try {
            fileInputStream = new FileInputStream(path);
            int length = fileInputStream.available();
            byteArr = new byte[length];
            fileInputStream.read(byteArr);
            byteArr = decode(byteArr);
            return defineClass(name,byteArr,0,byteArr.length);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
        return null;
    }
    //解码
    private byte[] decode(byte[] byteArr){
        for (int i=0;i<byteArr.length;i++){
            byteArr[i] =(byte)(255-(int)byteArr[i]);
        }
        return byteArr;
    }
}
