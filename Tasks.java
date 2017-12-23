import java.util.*;

public class Tasks {
         public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
            Iterator<Task> iter = tasks.iterator();
                 ArrayList<Task>  list = new ArrayList<>();
                 Task temp = null;
                 while (iter.hasNext()) {
                     temp = iter.next().clone();
                     if ((!(temp.nextTimeAfter(start) == null)) && (temp.nextTimeAfter(start).before(end) || temp.nextTimeAfter(start).equals(end))){
                         list.add(temp);
                     }
                 }
                 return list;
         }

       public static  SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end){
            SortedMap<Date, Set<Task>> calendar = new TreeMap<>();
            HashSet<Task> list = (HashSet<Task>) incoming(tasks, start, end);
            Task temp;
            Iterator<Task> iterator = list.iterator();
            Date date;
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (!temp.isRepeated()) {
                    if (!temp.nextTimeAfter(start).after(end)) {
                        if (calendar.get(temp.nextTimeAfter(start)) == null) {
                            HashSet<Task> ts = new HashSet<>();
                            ts.add(temp);
                            calendar.put(temp.nextTimeAfter(start), ts);
                        } else {
                            calendar.get(temp.nextTimeAfter(start)).add(temp);
                        }
                    }
                } else {
                    date = temp.nextTimeAfter(start);
                    while (!date.after(end)) {
                        if (calendar.get(date) != null) calendar.get(date).add(temp);
                        else {
                            HashSet<Task> ts = new HashSet<>();
                            ts.add(temp);
                            calendar.put(date, ts);
                        }
                        date = temp.nextTimeAfter(date);
                    }
                }
            }
           return calendar;
        }
}

