package restaurant.test;

import junit.framework.TestCase;
import restaurant.CashierAgent;
import restaurant.Check.CheckState;
import restaurant.test.mock.MockCustomer;
import restaurant.test.mock.MockMarket;
import restaurant.test.mock.MockWaiter;

public class V22BTests extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	CashierAgent cashier;
	MockMarket market1;
	MockMarket market2;
    MockWaiter waiter;
    MockCustomer customer;

    /**
     * This method is run before each test. You can use it to instantiate the class variables
     * for your agent and mocks, etc.
     */
	public void setUp() throws Exception
	{
		super.setUp();
		cashier = new CashierAgent("cashier");    
		market1 = new MockMarket("market1");
		market2 = new MockMarket("market2");
        customer = new MockCustomer("mockcustomer");                
        waiter = new MockWaiter("mockwaiter");
	}
	
    /**
     * This tests the cashier under very simple terms: one market is ready to be paid.
     */
	public void testNormativeScenarioOne()
	{
		assertEquals("The Cashier should have enough $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		
		cashier.MarketCost("Steak", 20.00, market1);
		
		assertEquals("The Cashier should still $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("The Cashier should have one market bill at the moment. It doesn't.", cashier.MarketBills.size(), 1);
		assertEquals("The Cashier should have the one market bill from the Mock Market. It doesn't.", cashier.MarketBills.get(0).market, market1);
		assertEquals("The Cashier should have the one market bill for Steak. It doesn't.", cashier.MarketBills.get(0).foodItem, "Steak");
		assertEquals("The Cashier should have the one market bill of $20.00. It doesn't.", cashier.MarketBills.get(0).finalTotal, 20.00);

		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		assertEquals("The Cashier should have $15 in profits. It doesn't.", cashier.profits, 15.00);
		assertEquals("The Cashier should have $20 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 20.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("MockMarket should have logged one event. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 1, market1.log.size());		
	}

	 /**
     * This tests the cashier under simple terms: two markets are ready to be paid.
     */
	public void testNormativeScenarioTwo()
	{
		assertEquals("The Cashier should have enough $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("MockMarket1 should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("MockMarket2 should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market2.log.toString(), 0, market2.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		
		cashier.MarketCost("Steak", 20.00, market1);
		
		assertEquals("The Cashier should still $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("The Cashier should have one market bill at the moment. It doesn't.", cashier.MarketBills.size(), 1);
		assertEquals("The Cashier should have the one market bill from the first Mock Market. It doesn't.", cashier.MarketBills.get(0).market, market1);
		assertEquals("The Cashier should have the one market bill for Steak from the first Mock Market. It doesn't.", cashier.MarketBills.get(0).foodItem, "Steak");
		assertEquals("The Cashier should have the one market bill of $20.00 from the first Mock Market. It doesn't.", cashier.MarketBills.get(0).finalTotal, 20.00);

		cashier.MarketCost("Chicken", 12.00, market2);
		
		assertEquals("The Cashier should still $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("The Cashier should have two market bills at the moment. It doesn't.", cashier.MarketBills.size(), 2);
		assertEquals("The Cashier should have the one market bill from the second Mock Market. It doesn't.", cashier.MarketBills.get(1).market, market2);
		assertEquals("The Cashier should have the one market bill for Chicken from the second Mock Market. It doesn't.", cashier.MarketBills.get(1).foodItem, "Chicken");
		assertEquals("The Cashier should have the one market bill of $12.00 from the second Mock Market. It doesn't.", cashier.MarketBills.get(1).finalTotal, 12.00);		
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should have one market bills at the moment. It doesn't.", cashier.MarketBills.size(), 1);
		assertEquals("The other market bill should be from the second Mock Market. It isn't.", cashier.MarketBills.get(0).market, market2);
		assertEquals("The Cashier should have enough $15 in profits. It doesn't.", cashier.profits, 15.00);
		assertEquals("The Cashier should have enough $20 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 20.00);
		assertEquals("MockMarket1 should have logged one event. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 1, market1.log.size());
		assertEquals("MockMarket2 should have logged no events. Instead, the MockMarket's event log reads: " 
				+ market2.log.toString(), 0, market2.log.size());
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		assertEquals("The Cashier should have enough $3 in profits. It doesn't.", cashier.profits, 3.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should have enough $32 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 32.00);
		assertEquals("MockMarket2 should have logged one event. Instead, the MockMarket's event log reads: " 
				+ market2.log.toString(), 1, market2.log.size());		
	}

	 /**
     * This tests the cashier under simple terms: one customer is ready to pay.
     */
	public void testScenarioThree()
	{
		customer.cashier = cashier;
		waiter.cashier = cashier;
		
		assertEquals("The Cashier should have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("The Cashier should have no checks at the moment. It does.", cashier.AllChecks.size(), 0);
		
        cashier.GiveMeCheck("7.98", customer, waiter);
        
		assertEquals("The Cashier should have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Created' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Created);
		assertEquals("The Check should be for 7.98. It isn't.", cashier.AllChecks.get(0).cost, 7.98);
		assertEquals("The Check should be for the Mock Customer. It isn't.", cashier.AllChecks.get(0).c, customer);
		assertEquals("The Check should be for the Mock Waiter. It isn't.", cashier.AllChecks.get(0).w, waiter);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());		
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Pending' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Pending);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an event logged. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 1, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());	
		
		customer.cashier.HereIsPayment(cashier.AllChecks.get(0), 7.98);

		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.CustomerHere);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());	

		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
        assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + cashier.AllChecks.get(0).s,
                cashier.AllChecks.get(0).s == CheckState.PaidOff);
		assertEquals("The Cashier should have $42.98 in profits. It doesn't.", cashier.profits, 42.98);
		assertEquals("The Cashier should have $42.98 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 42.98);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have logged two events. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 2, customer.log.size());	      
	}
	
	 /**
     * This tests the cashier under simple terms: one customer is ready to pay, but doesn't have enough money.
     */
	public void testScenarioFour()
	{
		customer.cashier = cashier;
		waiter.cashier = cashier;
		
		assertEquals("The Cashier should have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("The Cashier should have no checks at the moment. It does.", cashier.AllChecks.size(), 0);
		
        cashier.GiveMeCheck("7.98", customer, waiter);
        
		assertEquals("The Cashier should have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Created' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Created);
		assertEquals("The Check should be for 7.98. It isn't.", cashier.AllChecks.get(0).cost, 7.98);
		assertEquals("The Check should be for the Mock Customer. It isn't.", cashier.AllChecks.get(0).c, customer);
		assertEquals("The Check should be for the Mock Waiter. It isn't.", cashier.AllChecks.get(0).w, waiter);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());		
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Pending' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Pending);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an event logged. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 1, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());	
		
		customer.cashier.HereIsPayment(cashier.AllChecks.get(0), 0.0);

		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.CustomerHere);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());	

		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());	

		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
        assertTrue("CashierBill should contain a bill with state == NotPaidOff. It doesn't." + cashier.AllChecks.get(0).s,
                cashier.AllChecks.get(0).s == CheckState.NotPaidOff);
		assertEquals("The Cashier should have $27.02 in profits. It doesn't.", cashier.profits, 27.02);
		assertEquals("The Cashier should have $35.00 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $7.98 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 7.98);
		assertEquals("Customer should still have logged two events. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 2, customer.log.size());	      
	}
	
	 /**
     * This tests the cashier under simple terms: one customer is ready to pay and one market is ready to get paid. The market will prompt the cashier while the customer is there.
     */
	public void testScenarioFive()
	{
		customer.cashier = cashier;
		waiter.cashier = cashier;
		
		assertEquals("The Cashier should have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		assertEquals("The Cashier should have no checks at the moment. It does.", cashier.AllChecks.size(), 0);
		
        cashier.GiveMeCheck("7.98", customer, waiter);
        
		assertEquals("The Cashier should have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Created' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Created);
		assertEquals("The Check should be for 7.98. It isn't.", cashier.AllChecks.get(0).cost, 7.98);
		assertEquals("The Check should be for the Mock Customer. It isn't.", cashier.AllChecks.get(0).c, customer);
		assertEquals("The Check should be for the Mock Waiter. It isn't.", cashier.AllChecks.get(0).w, waiter);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an empty event log before Cashier's scheduler is called. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 0, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'Pending' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.Pending);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Waiter should still have an event logged. Instead, the Waiter's event log reads: " 
				+ waiter.log.toString(), 1, waiter.log.size());
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		
		customer.cashier.HereIsPayment(cashier.AllChecks.get(0), 7.98);

		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.CustomerHere);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		
		cashier.MarketCost("Steak", 20.00, market1);
		
		assertEquals("The Cashier should have one market bill at the moment. It doesn't.", cashier.MarketBills.size(), 1);
		assertEquals("The Cashier should have the one market bill from the Mock Market. It doesn't.", cashier.MarketBills.get(0).market, market1);
		assertEquals("The Cashier should have the one market bill for Steak. It doesn't.", cashier.MarketBills.get(0).foodItem, "Steak");
		assertEquals("The Cashier should have the one market bill of $20.00. It doesn't.", cashier.MarketBills.get(0).finalTotal, 20.00);
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should still be in the 'CustomerHere' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.CustomerHere);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		assertEquals("MockMarket should have an empty event log before Cashier's scheduler is called. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 0, market1.log.size());
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should have no market bills at the moment. It does.", cashier.MarketBills.size(), 0);
		assertEquals("The Cashier should have $15 in profits. It doesn't.", cashier.profits, 15.00);
		assertEquals("The Cashier should have $20 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 20.00);
		assertEquals("The Cashier should still have $35 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 35.00);
		assertEquals("MockMarket should have logged one event. Instead, the MockMarket's event log reads: " 
				+ market1.log.toString(), 1, market1.log.size());
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
		assertEquals("The Check should still be in the 'CustomerHere' state. It isn't." + cashier.AllChecks.get(0).s, cashier.AllChecks.get(0).s, CheckState.CustomerHere);
		assertEquals("The Cashier should still have $35 in profits. It doesn't.", cashier.profits, 35.00);
		assertEquals("Customer should still have an empty event log before Cashier's scheduler is called. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 0, customer.log.size());
		
		assertTrue("Cashier's PickAndExecuteAction should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("The Cashier should still have one check at the moment. It doesn't.", cashier.AllChecks.size(), 1);
        assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + cashier.AllChecks.get(0).s,
                cashier.AllChecks.get(0).s == CheckState.PaidOff);
		assertEquals("The Cashier should have $42.98 in profits. It doesn't.", cashier.profits, 42.98);
		assertEquals("The Cashier should have $42.98 in accumulated Revenue. It doesn't.", cashier.accumulatedRevenue, 42.98);
		assertEquals("The Cashier should still have $0 in accumulated Costs. It doesn't.", cashier.accumulatedCosts, 0.0);
		assertEquals("Customer should still have logged two events. Instead, the Customer's event log reads: " 
				+ customer.log.toString(), 2, customer.log.size());	
	}
	
	 /**
     * This tests the cashier under simple terms: one customer is ready to pay and one market is ready to get paid.
     * The market will prompt the cashier while the customer is there.
     * The cashier doesn't have enough to pay the market until the customer pays.
     */
	public void testScenarioSix()
	{
	
	}
}
