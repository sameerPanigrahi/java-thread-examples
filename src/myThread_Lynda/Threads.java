package myThread_Lynda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//required for useWaitAndNotify() method
class ThreadB extends Thread {
	int total;

	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				total += i;
			}
			notify();
		}
	}
}

// required for executor
class WorkerThread implements Runnable {

	private String message;

	WorkerThread() {
		super();
	}

	WorkerThread(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "(start) message " + message);

		// the thread now does some tasks, lets mimic this by putting the thread
		// to sleep for 2 seconds
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " (End)");
	}

}

public class Threads {

	// used for useSynchronizeMethods()
	static int counter = 1;

	static class PrintThreadStates {
		public void print(String comments, Thread t) {

			System.out.printf("%s -> Thread %s is %salive and in %s state and priority is %d \n",
					comments.equals("") ? "" : comments, t.getName(), t.isAlive() ? "" : "not ", t.getState(),
					t.getPriority());
		}
	};

	static Thread t1, t2;

	public static void main(String[] args) throws InterruptedException {

		// Exercise 1
		// diffThreadCreations();

		// Exercise 2
		// diffThreadStates();

		// Exercise 3
		// seeJoinThreads();

		// Exercise 4
		useSynchronizeMethods();

		// Exercise 5
		// useSynchronizeBlocks();

		// Exercise 6
		// useWaitAndNotify();

		// Exercise 7
		// useExecutor();

	}

	public static void diffThreadCreations() {
		// create a thread using a class that implements Runnable Interface
		(new Thread(new HelloRunnable())).start();

		// create a thread using a class that extends Thread class
		new HelloThread().start();

		Runnable r1 = new Runnable() {
			public void run() {
				System.out.println("hello from " + Thread.currentThread().getName()
						+ " a thread created by anonymous Runnable Object");
			}
		};

		Thread t1 = new Thread(r1, "RunnableObjectThread");

		t1.start();
	}

	public static void diffThreadStates() throws InterruptedException {

		PrintThreadStates pts = new PrintThreadStates();
		// pts.print(Thread.currentThread());

		Runnable r1 = new Runnable() {
			public void run() {
				// System.out.println("inside the run method of Runnable
				// Object");
				pts.print("in run method", Thread.currentThread());
			}
		};

		Thread t2 = new Thread(r1);
		t2.start();
		Thread.sleep(10);// put main thread to sleep
		pts.print("after main sleep", t2);

	}

	public static void seeJoinThreads() throws InterruptedException {

		Runnable r2 = new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++)
					System.out.println(Thread.currentThread().getName() + ": " + i);
			}
		};

		Runnable r1 = new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					if (i == 2) {
						Thread t2 = new Thread(r2, "t2");
						t2.start();

						// if the below t2.join() is commented out, then 2 threads will interleave.
						// if t2.join is used then the current thread t1 will wait till t2 finishes its
						// execution
						try {
							t2.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} // end if
					System.out.println(Thread.currentThread().getName() + ": " + i);
				} // end for loop
			}
		};

		Thread t1 = new Thread(r1, "t1");
		t1.start();

	}

	public static void useSynchronizeMethods() {

		class ID {
			public int getID() {
				return counter++;// inner class can access static variables like counter
			}
		}

		ID id = new ID();

		Runnable r1 = new Runnable() {
			public synchronized void run() {
				System.out.println(Thread.currentThread().getName() + ": ID: " + id.getID());
			}
		};

		Thread t1 = new Thread(r1, "t1");
		Thread t2 = new Thread(r1, "t2");

		t1.start();
		t2.start();
	}

	public static void useSynchronizeBlocks() {

		PrintThreadStates pts = new PrintThreadStates();

		class CountDown {
			public void countDown() {
				for (int i = 5; i >= 0; i--) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
				}
				System.out.println("Blast Off!");
				pts.print(Thread.currentThread().getName(), t1);
				pts.print(Thread.currentThread().getName(), t2);

			}
		}

		CountDown cd = new CountDown();

		Runnable r1 = new Runnable() {
			public void run() {
				pts.print(Thread.currentThread().getName(), t1);
				pts.print(Thread.currentThread().getName(), t2);
				synchronized (cd) {
					cd.countDown();
				}
			}
		};

		t1 = new Thread(r1, "t1");
		t2 = new Thread(r1, "t2");

		t1.start();
		t2.start();
	}

	public static void useWaitAndNotify() {
		ThreadB b = new ThreadB();
		b.start();
		synchronized (b) {
			try {
				System.out.println("Waiting for second thread to complete...");
				b.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Total is: " + b.total);
		}
	}

	public static void useExecutor() {

		ExecutorService exeService = Executors.newFixedThreadPool(5);

		for (int i = 0; i < +5; i++) {
			Runnable r1 = new WorkerThread("Thread" + i);
			exeService.execute(r1);
		}

		exeService.shutdown();

		if (exeService.isTerminated()) {

		}
	}
}
