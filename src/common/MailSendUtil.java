package common;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 * メール送信ユーティリティクラス
 */
public class MailSendUtil {

    private InternetAddress[] toAddress;
    private String fromAddress;
    private String subject;
    private String mainText;
    private String attachFilePath;

    public void setToAddress(InternetAddress[] toAddress) {
        this.toAddress = toAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }

    /**
     * メール送信処理
     */
    public void sendMail() {

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "localhost");
        properties.put("mail.debug", true);

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            // 送信先の設定
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, this.toAddress);
            // 送信元の設定
            mimeMessage.setFrom(this.fromAddress);
            // サブジェクトの設定
            mimeMessage.setSubject(this.subject);
            // 本文の設定
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(this.mainText);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // 添付ファイルの設定
            if(this.attachFilePath != null) {
                // 添付ファイルが指定されている場合
                MimeBodyPart file = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(this.attachFilePath);
                file.setDataHandler(new DataHandler(fds));

                // 添付ファイル名を設定
                File attachFile = new File(this.attachFilePath);
//                file.setFileName(MimeUtility.encodeText(attachFile.getName(), "ISO-2022-JP", null));
                file.setFileName(attachFile.getName());

                // MIMEマルチパートオブジェクトに添付ファイルを追加
                multipart.addBodyPart(file);
            }

            mimeMessage.setContent(multipart);

            // メール内容表示
//            mimeMessage.writeTo(System.out);

            // メールの送信
            Transport.send(mimeMessage);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
