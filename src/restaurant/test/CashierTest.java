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

            cashier.GiveMeCheck("7.98", customer, waiter);
            
            //check postconditions for step 1 and preconditions for step 2
            assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            assertEquals("Cashier should have 1 bill in it. It doesn't.", cashier.AllChecks.size(), 1);
             
            assertEquals(
                            "MockWaiter should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockWaiter's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            assertEquals(
                            "MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
                                            + waiter.log.toString(), 0, waiter.log.size());
            
            assertTrue("CashierBill should contain a bill of price = $7.98. It contains something else instead: $" 
                            + cashier.AllChecks.get(0).cost, cashier.AllChecks.get(0).cost == 7.98);
            
            assertTrue("CashierBill should contain a bill with the right customer in it. It doesn't.", 
                                    cashier.AllChecks.get(0).c == customer);
            
            cashier.HereIsPayment(cashier.AllChecks.get(0), 7.98);
            cashier.pickAndExecuteAnAction();
            
            assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + cashier.AllChecks.get(0).s,
                            cashier.AllChecks.get(0).s == CheckState.PaidOff);
    
    }//end one normal customer scenario
    
   
}
