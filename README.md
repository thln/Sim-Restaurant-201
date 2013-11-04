##Restaurant Project Repository

###Student Information
  + Name: Tam Henry Le Nguyen
  + USC Email: tamnguye@usc.edu
  + USC ID: 6154285255

###Resources
  + [Restaurant v1](http://www-scf.usc.edu/~csci201/readings/restaurant-v1.html)
  + [Agent Roadmap](http://www-scf.usc.edu/~csci201/readings/agent-roadmap.html)
  + View Design Documents and Interaction Diagrams in my 'Design Documents' folder.

###How to Import File
  + Please have Java and Eclipse installed on your computer.
  + Please clone my repository (use Git Bash if Windows, terminal for Linux or Mac)
    which is "restaurant_tamnguye.git"
  + Open Eclipse
  + File -> New -> Other
  + Choose Java Project from Existing Ant Buildfile
  + Click on Browse Button
  + Navigate to git repository that you cloned earlier (Should be restaurant_tamnguye)
  + Choose the build.xml file
  + Check the Link to the buildfile in the file system box
  + Press Finish

###How to Run Restaurant
  + Press the Start Button (or Ctrl+F11)
  + I have no starting waiters. Therefore, click on the waiter tab,
    fill in a name, and press 'Add Person' to add the waiter. A button
    should come up in the list.
  + I have no starting customers. Therefore, click on the customer tab,
    fill in a name, and press 'Add Person' to add the person. A button
    should come up in the list. You can check the Hungry Checkbox if you 
    wish to make the customer hungry immediately.
  + You can click on a customer at anytime to view their information. If 
    they are not hungry, you can click the hungry checkbox in the information
    panel to make them hungry again.
  + The front desk is located in the top left corner, the kitchen is located
    in the bottom right. 
  + You can click 'Add Table' to add up to 9 tables.
  + You can click "Pause" to pause or resume the agents at anytime. The agents
    will finish what they are doing before pausing.

###V2.1 Notes
  + The Cook has 10 of every item by default. Every item's low indicator is set at 1.
  + There are 3 markets implemented : Target, Walmart, and Costco. Target will only have one of every item in stock,
   thus ordering from target will automatically trigger ordering from Walmart.
  + I have added in "visuals" for the cashier, breakroom, and kitchen in order to make easier to understand.
  + I sped up my restaurant so everything moves quicker!
  + The Design Docs for the original 4 agents have an Additional page.
  + Key Bindings! You can use any of the following to create scenarios 
  *(NOTE: The first 4 bindings only affect customers who are waiting)*
    + "Ctrl+I" : Any customer waiting to be seated while there is a full restaurant will leave.
    + "Ctrl+B" : Any customer waiting to be seated will now dine and dash/eat regardless of cost.
    + "Ctrl+O" : Any customer waiting to be seated will now have $0, and will not be able to buy any item.
    + "Ctrl+P" : Any customer waiting to be seated will now have $8, and will only be able to afford a salad.
    + "Ctrl+L" : The inventory of the cook will be set to right before low (2). When a customer orders, the chef will begin to purchase more foods. 
    + "Ctrl+E" : The inventory of the cook will be emptied. The chef will immediately order more food.
  + I hope you enjoy my restaurant!

###V2.1 Scenarios
  + In order to make grading my restaurant more convenient for you, I will explain how to run each scenario! *Do not forget to restart the application each time.*
  + *One of every type of agent, no market interactions, customer orders, pays, and leaves*
    + Add a waiter, add a customer.
  + *No customers, cook orders low items from market, when food arrives, then customers arrive*
    + Ctrl+E. The cook won't be able to give food until the delivery arrives, so the customers will just wait. 
  + *Multiple customers, multiple waiters, cashier operating normally, no running out of food.*
    + Add the number of waiters, customers, and table as you would like.
  + *Waiter wants to go on break, he's told it's ok, goes on break when he finished all his current customers; goes on break, then goes off break.*
    + Add in a table, two waiters, and then two customers. Set the second waiter to "onBreak." The second waiter will finish his customer, then go on break. Read the console to follow what is happening.
    + The waiter will automatically return from break after a couple of seconds. This is fine, according to Professor Crowley (issue #41).
    + /*If the onBreak is unenabled after the waiter is done with his break, click on his button from the list waiter to refresh the infoPanel.
  + *Customer orders, food has run out, cook has obviously ordered but food hasn't arrived, Customer makes new choice.*
   + Ctrl+L. Add in two waiters. Add in two "Salad" people to have the amounts of salads set to 0. From there, you can add in *a lot* of people in order to have them reorder (one will eventually order a salad). 
   + You can't press Ctrl+P because the person will only be able to afford a salad, and will thus leave instead of making a new choice.
  + *Cook orders from a Market, but is told they can't fulfill his order; must order from one of his backup markets.*
    + Ctrl+E. The cook will order from Target, see that they do not have enough, and orders more from Walmart.
  + *Waiter wants to go on break, he's told it's NOT OK and must keep working.*
    + Add in one waiter. Attempt to press the "onBreak." It will "blink." Read the console, and you will see the waiter was rejected from going on break.
  + *Customer comes to restaurant and restaurant is full, customer is told and waits.*
    + By default, the customer will wait if the restaurant is full.
  + *Customer comes to restaurant and restaurant is full, customer is told and leaves.*
    + Add a customer, add a waiter. Add a customer. Ctrl+I. AThecustomer waiting will just leave. You can read the console to see that the customer has left.
  + *Customer doesn't have enough money to order anything and leaves.*
    + Add a customer. Ctrl+O. Any customer waiting will have $0. Add a waiter. The customer will sit down, see that he cannot afford anything, and then leave.
  + *Customer has only enough money to order the cheapest item (salad).*
    + Add a customer. Press Ctrl+P. Add a waiter. The customer will only be able to afford a salad, and thus order it.
  + *Customer has only enough money to order the cheapest item (salad); he orders it, then they run out of it and he leaves.*
    + Ctrl+L. Add in two waiters. Add in two "Salad" people to have the amounts of salads set to 0. Add another person, press Ctrl+P, to make the person only
    be able to afford a salad. Add a table and another waiter to seat the customer. The customer will order a salad, find out that there are no more, and leave.
  + *Customer orders, eats, but hasn't enough money to pay.
    + Add one customer. Ctrl+B. Any customer waiting will eat, regardless of cost (customer has $0). Add a waiter. This will incur a debt from the customer. The restaurant also keeps tracks of debts. If the customer returns, he will still dine and dish (once a cheater always a cheater) since he will only have $0. He will add his debt to the bill, and his debt will increase.

####V2.2 Scenarios
  + V2.2A: To test the first two scenarios as well as the 'extra credit' scenario in V2.2A, please run the JUnit test named 'V22BTests.java.' They are testCases 1, 2, and 6.
  + V2.2B: The JUnit tests tests the following
    + 1 - One order fulfilled by market, one bill paid
    + 2 - One order fulfilled by two markets, two bills paid
    + 3 - One Customer ready to pay, has money
    + 4 - One customer ready to pay, but doesn't have enough money
    + 5 - One customer ready to pay and one bill ready to be paid (bill will take priority)
    + 6 - One customer ready to pay, and one bill ready to be paid, but cashier will not have enough money until the customer pays (customer takes priority)
    + In Scenarios 3-6, I also test the waiter creating a check with the cashier.
  + V2.2C: Last I checked, no concurrent modification errors! :) I synchronized (solution 1) Cashier, Cook, Market, and Host. I caught Concurrenet Modification exceptions (solution 2) in Waiter. I did not change Customers.
  + V2.2D: The customer's waiting area is on the left side of the screen.
  + The waiter's home position is on the top side of the screen.
  + The left side of the kitchen is plating/ready area. The right side of the kitchen is the cooking area. I did not do the extra credit.
  + V2.2E: I have made commits on October 24th, November 2nd, and November 3rd.
  + I will tag my submission.
  + I think my commit comments are pretty awesome, and I hope you do too.


###Issues
  + Currently, none :)
  + The application likes to play mindgames and stop randomly. Just restart it! 