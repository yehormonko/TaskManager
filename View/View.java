package View;

import Controller.Controller;
import Model.ArrayTaskList;
import Model.Task;
import Model.Tasks;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class View {
	private Controller controller;
	public  View(Controller controller){
		this.controller=controller;
	}
	public void menu() {
		System.out.println("________________________________\n"+
				"Choose action:\n" +
				"1. Print tasks\n" +
				"2. Add task\n" +
				"3. Remove task\n" +
				"4. Show tasks frome date to date\n" +
				"5. Show calendar tasks\n" +
				"6. EXIT\n____________________________________   ");
		Scanner scanner = new Scanner(System.in);
		int choose = 0;
		if (scanner.hasNextInt()) {
			choose = scanner.nextInt();
		} else {
			this.menu();
			return;
		}
		System.out.println("choose = " + choose);
		if (choose < -1 || choose > 6) {
			this.menu();
			return;
		}
		boolean stop = false;
		switch (choose) {
			case 1: {
				printTask();
				System.out.println("Return to menu? y/n");
				{
					if (ask())
						menu();
					return;
				}
			}
			case 2: {
				while (!stop) {
					stop = addTask();
				}
				stop = false;
				System.out.println("Return to menu? y/n");
				{
					if (ask())
						menu();
					return;
				}
			}
			case 3: {
				while (!stop) {
					stop = remove();
				}
				stop = false;
				System.out.println("Return to menu? y/n");
				{
					if (ask())
						menu();
					return;
				}
			}
			case 4: {
				this.printFromTo();
				System.out.println("Return to menu? y/n");
				{
					if (ask())
						menu();
					return;
				}
			}
			case 5:{
				this.calend();
				System.out.println("Return to menu? y/n");
				{
					if (ask())
						menu();
					return;
				}
			}
			case 6:{
				System.out.println("Are you sure? y/n");
				if (ask())
				return;
				if(!ask()){
					menu();
					return;
				}
			}
		}
	}
	public void printTask(){
		ArrayTaskList list = new ArrayTaskList();
		list=controller.getArrayTaskList();
		for(int i = 0;i< list.size();i++){
			System.out.println(i+1+". "+list.getTask(i).toString());
		}
	}
	public boolean addTask() {
		String title;
		Date time, start, end;
		boolean repeated, active;
		Scanner scanner = new Scanner(System.in);
		System.out.println("print name");
		title = scanner.next();
		System.out.println("Is it repeated? y/n");
		boolean choise = this.ask();
		System.out.println(choise);
//		if (yn != 'y' && yn != 'n') {
//			System.out.println("Print y or n");
//			return false;
//		}
		Task created;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS", Locale.ENGLISH);
	//	System.out.println("is it repeated& y/n");
		if (!choise) {
			repeated =false;
			created = new Task(title,this.inputDate());
		} else {
			repeated=true;
			String date, tm;
			System.out.println("START");
			start = this.inputDate();
			System.out.println("END");
			end = this.inputDate();
			System.out.println("print interval in minutes");
			int interval=input();
//			if(scanner.hasNextInt()){
//				interval = scanner.nextInt();//sdklfjsdklfjsdklfjsdklfjdl
//			} else{
//				System.out.println("print number of");
//				return false;
//			}
			created = new Task(title,start,end,interval);
		}
	//	yn='0';
		System.out.println("would you like make it active? y/n");
		choise = ask();
		if(choise) created.setActive(true);
		else created.setActive(false);
//		else {
//			System.out.println("\"y\" or \"n\"");
//			return false;
//		}
		controller.addTask(created);
		return true;
	}
	public boolean remove(){
		this.printTask();
		int choise;
		System.out.println("Which you want to delete?");
		Scanner scanner = new Scanner(System.in);
		if(!scanner.hasNextInt()) {
			System.out.println("print correct number");
			return false;
		}
		choise=scanner.nextInt();
		if(choise<1||choise>controller.getArrayTaskList().size()) {
			System.out.println("print correct number");
			return false;
		}
		
		controller.remove(controller.getArrayTaskList().getTask(choise-1));
		return true;
	}
	private Date inputDate(){
		String date, tm;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS", Locale.ENGLISH);
		Scanner scanner = new Scanner(System.in);
		System.out.println("print date in format yyyy-MM-dd");
		date = scanner.next();
		Date newDate;
		System.out.println("ptint fime in format HH:mm");
		tm = " " + scanner.next() + ":00.00";
		try {
			newDate = format.parse(date + tm);
		} catch (ParseException e) {
			System.out.println("Wrong date format");
			return  this.inputDate();
		}
		return newDate;
	}
	public void printFromTo(){
		ArrayTaskList list = new ArrayTaskList();
		System.out.println("FROM");
		Date start = this.inputDate();
		System.out.println("TO");
		Date end = this.inputDate();
		list = (ArrayTaskList) controller.getArrayTaskList().incoming(start,end);
		for(int i = 0;i< list.size();i++){
			System.out.println(i+1+". "+list.getTask(i).toString());
		}
	}
	public void calend(){
		System.out.println("FROM");
		Date start = this.inputDate();
		//Date start = new Date(0);
		System.out.println("TO");
		Date end = this.inputDate();
		//Date end = new Date(100000);
		System.out.println("+");
		SortedMap<Date, Set<Task>> map = Tasks.calendar(controller.getArrayTaskList(),start,end);
		for (Map.Entry entry : map.entrySet()) {
			if(map.isEmpty()){
				System.out.println("Empty");
				return;
			}
			System.out.println(entry.getKey());
			HashSet<Task> tasks = (HashSet<Task>) entry.getValue();
			for (Task task:tasks) {
				System.out.println("    "+task.toString());
			}
		}
	}
	private boolean ask(){
		Scanner scanner = new Scanner(System.in);
		char choose = scanner.next().charAt(0);
		if (choose == 'y')  return true;
		if(choose=='n') return false;
		System.out.println("\"y\" or \"n\"");
		return this.ask();
	}
	private int input(){
		Scanner scanner = new Scanner(System.in);
		if(scanner.hasNextInt()){
			return scanner.nextInt();
		}
		else{
			this.input();
			System.out.println("print number");
		}
		return 0;
	}
	}

