//package com.kilim;
//
//import kilim.Pausable;
//import kilim.Task;
//
//public class FilberTestMain {
//
//    //    public static void main(String[] args) {
//    //        Mailbox<Calculation> sharedMailbox = new Mailbox<Calculation>();
//    //
//    //        Task deferred = new DeferredDivision(sharedMailbox);
//    //        Task calculator = new Calculator(sharedMailbox);
//    //
//    //        deferred.start();
//    //        calculator.start();
//    //    }
//
//    public static void main(String[] args) {
//        Task task = new Task() {
//            @Override
//            public void execute() throws Pausable {
//                System.out.println("hello");
//                yield();
//                System.out.println("world");
//            }
//        };
//        task.resume();
//        System.out.println("hey there");
//        task.resume();
//    }
//}
