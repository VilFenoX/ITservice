package com.itservice.model;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Serializator {
    public boolean serialization(StringForm form, File f){
        boolean flag = false;
        //File f = new File(fileName);
        ObjectOutputStream outputStream = null;
        try {
            FileOutputStream fos = new FileOutputStream(f);
            if(fos != null){
                outputStream = new ObjectOutputStream(fos);
                outputStream.writeObject(form);
                flag = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    public StringForm deserialization(File file) throws InvalidObjectException {
       // File fr = new File(fileName);
        ObjectInputStream inputStream = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            inputStream = new ObjectInputStream(fis);
            StringForm stringForm = (StringForm) inputStream.readObject();
            return stringForm;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new InvalidObjectException("Обьект не восстановлен");
    }
}
