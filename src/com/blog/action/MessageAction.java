package com.blog.action;

import java.sql.Timestamp;
import java.util.List;

import com.blog.dao.MessageDao;
import com.blog.entriy.LeaveWord;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MessageAction extends ActionSupport{
	private MessageDao messageDao;
	public MessageDao getMessageDao() {
		return messageDao;
	}
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	public String wordContent;
	public String getWordContent() {
		return wordContent;
	}
	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}
	
	public String newLeaveWord(){
		try{
			LeaveWord leaveWord = new LeaveWord();
			leaveWord.setWordContent(wordContent);
			leaveWord.setWordTime(new Timestamp(System.currentTimeMillis()));
			String msg = messageDao.newLeaveWord(leaveWord);
			ActionContext.getContext().getSession().put("operations", msg==null ? "���Գɹ�!" : "����ʧ��:" + msg + " �쳣λ��: messageAction!newLeaveWord��" );
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "����ʧ��:" + e.toString() + " �쳣λ��: messageAction!newLeaveWord��" );
			return "errors";
		}
		return "newLeaveWord";
	}
	
	public String leaveWordJspGetAllWords(){
		List<LeaveWord> list = null;
		try{
			list = messageDao.leaveWordJspGetAllWords();
			if(list == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("leaveWordJspGetAllWords", list);
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "����ʧ��:" + e.toString() + " �쳣λ��: messageAction!leaveWordJspGetAllWords��" );
			return "errors";
		}
		return "leaveWordJspGetAllWords";
	}
}
