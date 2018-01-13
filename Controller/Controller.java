package Controller;

import Model.ArrayTaskList;
import Model.Task;
import Model.TaskIO;
import Model.Tasks;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Controller {
	private ArrayTaskList list = new ArrayTaskList();
	private File file = new File("Tasks");
	public Controller(){
		TaskIO.readBinary(list,file);
	}
	public void addTask(Task task) {
		list.add(task);
		TaskIO.writeBinary(list, file);
	}
	public void remove(Task task){
		list.remove(task);
		TaskIO.writeBinary(list,file);
	}
	public String display(){
	return list.toString();
	}
	public String incoming(Date start, Date end){
		return Tasks.incoming(list,start,end).toString();
	}
	public ArrayTaskList getArrayTaskList(){
		return list;
	}
}
