package restaurant.test;

import restaurant.CashierAgent;
import restaurant.Check;
import restaurant.Check.CheckState;
import restaurant.test.mock.EventLog;
//import restaurant.CashierAgent.cashierBillState;
//import restaurant.WaiterAgent.Bill;
import restaurant.test.mock.MockCustomer;
import restaurant.test.mock.MockWaiter;
import junit.framework.*;

public class CashierTest extends TestCase
{
    //these are instantiated for each test separately via the setUp() method.
    CashierAgent cashier;
    MockWaiter waiter;
    MockCustomer customer;
    
    
    
    /**
     * This method is run before each test. You can use it to instantiate the class variables
     * for your agent and mocks, etc.
     */
    public void setUp() throws Exception{
            super.setUp();                
            cashier = new CashierAgent("cashier");                
            customer = new MockCustomer("mockcustomer");                
            waiter = new MockWaiter("mockwaiter");
    }        
    /**
     * This tests the cashier under very simple terms: one customer is ready to pay the exact bill.
     */
    public void testOneNormalCustomerScenario()
    {
            //setUp() runs first before this test!
            
            customer.cashier = cashier;//You can do almost anything in a unit test.                        
            Check mockCheck = new Check("Steak", customer, waiter);
            //check preconditions
            assertEquals("Cashier should have 0 bills in it. It doesn't.",cashier.AllChecks.size(), 0);                
            //assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
              //                              + cashier.log.toString(), 0, cashier.log.size());
            
            //step 1 of the test
            //public Bill(Cashier, Customer, int tableNum, double price) {
            //Check check = new Check("Steak", customer, waiter); //previous bill stuff(cashier, customer, 2, 7.98);
            //cashier.HereIsCheck(check);//send the message from a waiter
            cashier.GiveMeCheck("7.98", customer, waiter);
            
            //check postconditions for step 1 and preconditions for step 2
            assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            assertEquals("Cashier should have 1 bill in it. It doesn't.", cashier.AllChecks.size(), 1);
            
            //**********Check this
            //assertFalse("Cashier's scheduler should have returned false (no actions to do on a bill from a waiter), but didn't.", cashier.pickAndExecuteAnAction());
            
            assertEquals(
                            "MockWaiter should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockWaiter's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            assertEquals(
                            "MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            //step 2 of the test
            //cashier.ReadyToPay(customer, bill);
            //customer.HereIsYourCheck(new Check("Steak", customer, waiter));
           //cashier.HereIsPayment();
            //MockCustomer will autopay?
            //cashier.HereIsPayment(mockCheck, 15.99);
            
            //check postconditions for step 2 / preconditions for step 3
            //assertTrue("CashierBill should contain a bill with state == customerApproached. It doesn't.",
             //               cashier.AllChecks.get(0).s == CheckState.CustomerHere);
            
            //not necessary
            //assertTrue("Cashier should have logged \"Received ReadyToPay\" but didn't. His log reads instead: " 
              //              + cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received ReadyToPay"));

            assertTrue("CashierBill should contain a bill of price = $7.98. It contains something else instead: $" 
                            + cashier.AllChecks.get(0).cost, cashier.AllChecks.get(0).cost == 7.98);
            
            assertTrue("CashierBill should contain a bill with the right customer in it. It doesn't.", 
                                    cashier.AllChecks.get(0).c == customer);
            
            
            //step 3
            //NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
            //
           //assertTrue("Cashier's scheduler should have returned true (needs to react to customer's ReadyToPay), but didn't.", 
             //                       cashier.pickAndExecuteAnAction());
            cashier.HereIsPayment(cashier.AllChecks.get(0), 7.98);
            cashier.pickAndExecuteAnAction();
            
            
            //check postconditions for step 3 / preconditions for step 4
            //Unnecessary
            //assertTrue("MockCustomer should have logged an event for receiving \"HereIsYourTotal\" with the correct balance, but his last event logged reads instead: " 
             //               + customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourTotal from cashier. Total = 7.98"));
    
                    
            //assertTrue("Cashier should have logged \"Received HereIsMyPayment\" but didn't. His log reads instead: " 
              //              + cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received HereIsMyPayment"));
            
            //unnecessary
            //assertTrue("CashierBill should contain changeDue == 0.0. It contains something else instead: $" 
              //              + cashier.AllChecks.get(0).changeDue, cashier.AllChecks.get(0).changeDue == 0);
            
            
            
            //step 4
            //Check this
            //assertTrue("Cashier's scheduler should have returned true (needs to react to customer's ReadyToPay), but didn't.", 
             //                       cashier.pickAndExecuteAnAction());
            
            //check postconditions for step 4
            //assertTrue("MockCustomer should have logged an event for receiving \"HereIsYourChange\" with the correct change, but his last event logged reads instead: " 
             //               + customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourChange from cashier."));
    
            
            assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + cashier.AllChecks.get(0).s,
                            cashier.AllChecks.get(0).s == CheckState.PaidOff);
            
            //Check this
            //assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
              //              cashier.pickAndExecuteAnAction());
            
    
    }//end one normal customer scenario
    
   
}
