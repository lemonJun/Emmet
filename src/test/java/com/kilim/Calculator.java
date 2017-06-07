package com.kilim;

import java.math.RoundingMode;

import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;

public class Calculator extends Task {

    private Mailbox<Calculation> mailbox;

    public Calculator(Mailbox<Calculation> mailbox) {
        super();
        this.mailbox = mailbox;
    }

    @Override
    public void execute() throws Pausable, Exception {
        while (true) {
            Calculation calc = mailbox.get(); // blocks
            if (calc.getAnswer() == null) {
                calc.setAnswer(calc.getDividend().divide(calc.getDivisor(), 8, RoundingMode.HALF_UP));
                System.out.println("Calculator determined answer");
                mailbox.putnb(calc);
            }
            Task.sleep(1000);
        }
    }
}