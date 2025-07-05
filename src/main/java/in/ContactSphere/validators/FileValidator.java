package in.ContactSphere.validators;

import java.io.IOException;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

import in.ContactSphere.validators.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{

    private static final long MAX_FILE_SIZE = 1024*1024*5;  //5mb

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if(file==null || file.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("file cannot be empty").addConstraintViolation();
            return false;
        }

        if(file.getSize()> MAX_FILE_SIZE){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("file size should not be greater").addConstraintViolation();
            return false;

        }

        // try {
        //    BufferedImage iamge =  ImageIO.read(file.getInputStream());
        // } catch (IOException e) {
            
        //     e.printStackTrace();
        // }

        

        return true;


        
    }

}
