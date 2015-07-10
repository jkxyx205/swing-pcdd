package com.rick;

import com.rick.model.Email;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;

@Service
public class EmailSender {
		private static final Logger logger =  LoggerFactory.getLogger(EmailSender.class);
	
	 	@Resource
	    private JavaMailSender mailSender;
	    
	 	@Resource
	    private TaskExecutor taskExecutor;
	 	
	    /**
	     * 同步发送邮件
	     * 
	     * @param email
	     * @throws java.io.IOException
	     */
	    private void sendMailBySynchronizationMode(Email email) throws MessagingException, IOException {
	    	 MimeMessage mailMessage = mailSender.createMimeMessage(); 
			 MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true,"UTF-8");
			 
	         helper.setFrom(email.getFrom());//发件人
	         
	         helper.setTo(email.getTo());//收件人
	         if(ArrayUtils.isNotEmpty(email.getCc()))
	        	 helper.setCc(email.getCc());
	         //helper.setBcc("administrator@chinaptp.com");//暗送   
	         //helper.setReplyTo("xxx@sina.com");//回复到   
	         helper.setSubject(email.getSubject());//邮件主题    
	         helper.setText(email.getText(), true);//true表示设定html格式  
	         //添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源       很少用了
	         //helper.addInline("a", new File("f:/DSC01446.JPG")); //附件内容         
	                  
	         // 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题        
	         if (CollectionUtils.isNotEmpty(email.getAttachments())) {
	        	 for (File file : email.getAttachments()) {
	        		   helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);  
	        	 }
	         }
	         
	         mailSender.send(mailMessage);
	    }
	    
	    
	    /**
	     * 异步发送邮件
	     * @param email
	     */
		public void launch(final Email email) {
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						sendMailBySynchronizationMode(email);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			});
		}
}
