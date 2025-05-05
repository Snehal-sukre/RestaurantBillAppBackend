package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Bill;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.BillRepository;

@Service("billService")
public class BillService {
	
	@Autowired
	BillRepository billRepo;
	
	public Bill generateBill(int orderId) {
	    double totalAmt = billRepo.getTotalAmount(orderId);

	    double discountPer = 0;
	    double taxPercent = 0;

	    if (totalAmt >= 500 && totalAmt <= 1000) {
	        discountPer = 5;
	        taxPercent = 1.5;
	    } else if (totalAmt > 1000 && totalAmt <= 2000) {
	        discountPer = 10;
	        taxPercent = 2;
	    } else if (totalAmt > 2000) {
	        discountPer = 15;
	        taxPercent = 2.5;
	    }

	    double discount = totalAmt * discountPer / 100;
	    double discountedAmt = totalAmt - discount;
	    double cgst = discountedAmt * taxPercent / 100;
	    double sgst = discountedAmt * taxPercent / 100;
	    double grandTotal = discountedAmt + cgst + sgst;

	    Bill bill = new Bill();
	    bill.setOrderId(orderId);
	    bill.setTotalAmount(totalAmt);
	    bill.setDiscount(discount);
	    bill.setCgst(cgst);
	    bill.setSgst(sgst);
	    bill.setGrandTotal(grandTotal);

	    int billId = billRepo.generateBill(bill);
	    bill.setBillId(billId); // ✅ set generated ID

	    // ✅ Additional info
	    bill.setStaffname(billRepo.getWaiterName(orderId));
	    bill.setTableId(billRepo.getTableNumber(orderId));
	    bill.setItems(billRepo.getOrderItems(orderId));

	    // ✅ Mark table as available
	    billRepo.updateTableStatusToAvailable(orderId);

	    // ✅ Mark order as completed
	    billRepo.markOrderCompleted(orderId);

	    return bill;
	}
	
	public List<Bill> getAllBills()
	{
		return billRepo.getAllBills();
	}
	
	public Bill getBillByOrderId(int orderId) {
        return billRepo.getBillByOrderId(orderId);
    }

    public List<OrderItem> getOrderItems(int orderId) {
        return billRepo.getOrderItems(orderId);
    }
}
