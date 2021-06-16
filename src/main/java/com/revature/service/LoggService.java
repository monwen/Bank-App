package com.revature.service;

import com.revature.dao.TransactionDao;
import com.revature.models.Transaction;
import com.revature.utils.AppLogger;

public class LoggService {
	public static void getTransLog() {
		TransactionDao tDao = new TransactionDao();
		Transaction t = tDao.getLast();
		AppLogger.logger.info("transaction id: "
				+ t.getId()
				+", from account: "
				+ t.getFrom()
				+", to account"
				+ t.getTo()
				+", method: "
				+t.getMethodType()
				+", amount: "
				+t.getAmount()
				+", time: "
				+t.getTime()
);	
	}
}
