package com.jing.system.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * 发送邮件<br>
 * 示例：<br>
 * public static void main(String[] args) {
		// smtp服务器
		String smtp = "smtp.163.com";
		// 邮件显示名称
		String from = "cactus_jing@163.com";
		// 发件人真实的账户名
		String username = "cactus_jing@163.com";
		// 发件人密码
		String password = "12345";
		FrameMailUtil theMail = new FrameMailUtil(smtp, from, username, password);
		String to = "503490146@qq.com";// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "测试邮件";// 邮件标题
		String content = "你好！";// 邮件内容
		boolean bool = theMail.send(to, copyto, subject, content);
		LOGGER.info(bool);
	}
 * @author yuejing
 * @date 2016年4月30日 下午7:23:52
 * @version V1.0.0
 */
public class FrameMailUtil {
	
	private static final Logger LOGGER = Logger.getLogger(FrameMailUtil.class);
	
	private MimeMessage mimeMsg;
	private Session session;
	private Properties props;
	private String username;
	private String password;
	private Multipart mp;

	/**
	 * 构造函数
	 * @param smtp		smtp
	 * @param from		发送邮箱
	 * @param username	用户名
	 * @param password	密码
	 */
	public FrameMailUtil(String smtp, String from, String username, String password) {
		this.username = username;
		this.password = password;
		props = System.getProperties();
		props.put("mail.smtp.host", smtp);
		props.put("mail.smtp.auth", "true");
		try {
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			LOGGER.error("获取邮件会话错误！" + e);
			return;
		}
		try {
			mimeMsg = new MimeMessage(session);
		} catch (Exception e) {
			LOGGER.error("创建MIME邮件对象失败！" + e);
			return;
		}
		try {
			//发信人
			mimeMsg.setFrom(new InternetAddress(from));
		} catch (Exception e) {
			LOGGER.error("发送邮件异常: " + e.getMessage(), e);
			return;
		}
	}

	/**
	 * 定义邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			LOGGER.error("定义邮件主题发生错误！");
			return false;
		}
	}

	/**
	 * 定义邮件正文
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent(mailBody, "text/html;charset=UTF-8");
			mp = new MimeMultipart();
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			LOGGER.error("定义邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * 定义收信人
	 * @param to
	 * @return
	 */
	public boolean setTo(String to) {
		if (to == null) {
			return false;
		}
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			LOGGER.error("设置收件人异常: " + e.getMessage());
			return false;
		}
	}

	/**
	 * 定义抄送人
	 * @param copyto
	 * @return
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null) {
			return false;
		}
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			LOGGER.error("设置抄送人异常: " + e.getMessage());
			return false;
		}
	}

	//发送邮件模块
	private boolean sendOut() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			LOGGER.info("邮件发送中....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username, password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			LOGGER.info("发送成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			LOGGER.error("发送失败！" + e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 调用sendOut方法完成发送
	 * @param to		接收人
	 * @param title		标题
	 * @param content	正文
	 * @return
	 */
	public boolean send(String to, String title, String content) {
		if (!setSubject(title)) {
			return false;
		}
		if (!setBody(content)) {
			return false;
		}
		if (!setTo(to)) {
			return false;
		}
		if (!sendOut()) {
			return false;
		}
		return true;
	}

	/**
	 * 调用sendOut方法完成发送
	 * @param to		接收人
	 * @param copyto	抄送人
	 * @param title		标题
	 * @param content	正文
	 * @return
	 */
	public boolean send(String to, String copyto, String title, String content) {
		if (!setSubject(title)) {
			return false;
		}
		if (!setBody(content)) {
			return false;
		}
		if (!setTo(to)) {
			return false;
		}
		if (!setCopyTo(copyto)) {
			return false;
		}
		if (!sendOut()) {
			return false;
		}
		return true;
	}

}