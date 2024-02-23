package com.pro06.service;

import com.pro06.dto.email.EmailMessage;
import com.pro06.dto.email.OpeningRequest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private static final String ADMIN_ADDRESS = "sendjin4@gmail.com";
    private final SpringTemplateEngine templateEngine;
    public static final String ePw = createKey();

    //회원 인증 이메일 보내기
    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //if (type.equals("password")) userService.SetTempPassword(emailMessage.getTo(), authNum);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage);
            return authNum;
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    // 1대1 문의
    public void otosendMail(EmailMessage emailMessage, String type) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //if (type.equals("password")) userService.SetTempPassword(emailMessage.getTo(), authNum);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(otoContext(emailMessage, type), true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    //인증번호 및 임시 비밀번호 생성기
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    public String otoContext(EmailMessage emailMessage, String type) {
        Context context = new Context();
        context.setVariable("message", emailMessage);
        return templateEngine.process(type, context);
    }


    //랜덤 인증 코드 생성기2
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));                      // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));  // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));                      // 0~9
                    break;
            }
        }
        return key.toString();
    }



    public void sendMailTest(EmailMessage emailMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        log.info("service 시작 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        log.info("emailMessage ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + emailMessage);

        //if (type.equals("password")) userService.SetTempPassword(emailMessage.getTo(), authNum);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText("그냥 이렇게 ", false); // 메일 본문 내용, HTML 여부
//            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
            log.info("mimeMessageHelper ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + mimeMessageHelper);
            mailSender.send(mimeMessage);
            log.info("Success");
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }
}
