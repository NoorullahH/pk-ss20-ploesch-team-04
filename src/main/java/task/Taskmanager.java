package task;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import category.Category;
import category.Categorymanager;
import contributor.Contributor;
import contributor.Contributormanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import weekday.Weekday;

public class Taskmanager {
	
	private static Taskmanager INSTANCE;
	
	private ObservableList<Task> tasks;
	private static int size;
	
	/**
     * this method returns the single instance of the Taskmanager
     * @return the single instance of the Taskmanager
     */
	public static synchronized Taskmanager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Taskmanager();
		}
		return INSTANCE;
	}
	
	/**
     * private constructor which initializes the Taskmanager
     */
	private Taskmanager() {
		this.tasks = FXCollections.observableArrayList();
	}
		
	/**
     * this method returns a list of the tasks
     * @return list of the tasks
     */
	public ObservableList<Task> getTasks() {
		return tasks;
	}
	
	/**
     * this method adds a new Task to the Taskmanager
     * @param task 	the Task which should be added to the Taskmanager
     * @return indicates if the Task was added successfully
     * 
     */
	public boolean addTask(Task task) {
		if(tasks == null) {
			return false;
		}
		tasks.add(task);
		size++;
		return true;
	}
	
	/**
     * this method removes a Task from the Taskmanager
     * @param task 	the Task which should be removed to the Taskmanager
     * @return indicates if the Task was removed successfully
     * 
     */
	public boolean removeTask(Task task) {
		if(tasks == null) {
			return false;
		}
		int index = -1;	
		for(Task t:tasks) {
			if(t.getTaskNumber()==task.getTaskNumber()) {
				index = tasks.indexOf(t);
			}
		}
		if(index==-1) {
			return false;
		}else {
			tasks.remove(index);
			size--;
			return true;
		}
	}
	
	/**
     * this method removes a Category from all tasks of the Taskmanager
     * @param category 	the name of the Category
     * @return indicates if the Category was removed successfully
     * 
     */
	public boolean removeCategoryFromTasks(String category) {
		if(tasks == null) {
			return false;
		}
		int index = -1;
		int taskIndex = 0;
		for(Task t:tasks) {
			for(Category c:t.getCategories()) {
				if(c.getCategory().equals(category)) {
					index = t.getCategories().indexOf(c);
				}
			}
			if(index!=-1) {
				t.getCategories().remove(index);
			}
			index = -1;
		}
		return true;
	}
	
	/**
     * this method checks if the tasks have been initialized
     * @return indicates if tasks have already been initialized
     */
	public boolean isEmpty() {
		if(tasks == null) {
			return false;
		}else {
			return tasks.isEmpty();
		}
	}
	
	/**
     * this method sets the tasks to null
     */
	public void setTasksNull() {
		tasks = null;
	}
	
	/**
     * this method handles a recurrent Task
     * the method first checks if a task is repeated monthly or weekly
     * after that the method checks if the task is repeated to a specific date or repeated by a number
     * if the creationDate has been exceeded, a new task can be added to the Taskmanager
     * the new created task is repeated from now on and the task passed in the parameter is no longer repeated
     * @param Task 	the Task which should be checked
     * @return new added Task
     */
	public Task handleRecurrentTasks(Task t) {
		
		Task newTask = null;
		
		if(t.isRecurrent()) {
			if(t.isMonthly()) {
				if (t.getMonthday() == 31 && (t.getCreationDate().plusMonths(1).getMonthValue() == 4 || t.getCreationDate().plusMonths(1).getMonthValue() == 6
						|| t.getCreationDate().plusMonths(1).getMonthValue() == 9 || t.getCreationDate().plusMonths(1).getMonthValue() == 11)) {
					t.setMonthday(30);
				}else if (t.getMonthday() > 29 && t.getCreationDate().plusMonths(1).getMonthValue() == 2 && t.getCreationDate().isLeapYear()) {
					t.setMonthday(29);
				} else if (t.getMonthday() > 28 && t.getCreationDate().plusMonths(1).getMonthValue() == 2 && !t.getCreationDate().isLeapYear()) {
					t.setMonthday(28);
				}
				
				if(t.getNumberOfRepetitions()>0){
					if(LocalDate.now().isAfter(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()))) {
						newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null , t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.getMonthday(), (t.getNumberOfRepetitions()-1), null);
						newTask.setCreationDate(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()));
						tasks.add(newTask);
						for(Task task:tasks) {
							if(task.getTaskNumber()==t.getTaskNumber()) {
								task.setNumberOfRepetitions(0);
							}
						}
					}
				}else if(t.getRepetitionDate()!=null) {
					if(LocalDate.now().isAfter(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()))) {
						newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null , t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.getMonthday(), 0, t.getRepetitionDate());
						newTask.setCreationDate(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()));
						tasks.add(newTask);
						for(Task task:tasks) {
							if(task.getTaskNumber()==t.getTaskNumber()) {
								task.setRepetitionDate(null);
							}
						}
					}
				}
			}else if(t.isWeekly()) {
				if(t.getNumberOfRepetitions()>0){
					if(LocalDate.now().isAfter(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())))) {
						newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null, t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.getWeekday(), (t.getNumberOfRepetitions()-1), null);
						newTask.setCreationDate(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())));
						tasks.add(newTask);
						for(Task task:tasks) {
							if(task.getTaskNumber()==t.getTaskNumber()) {
								task.setNumberOfRepetitions(0);
							}
						}
					}
				}else if(t.getRepetitionDate()!=null) {
					if(LocalDate.now().isAfter(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())))) {
						newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null, t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.getWeekday(), 0 , t.getRepetitionDate());
						newTask.setCreationDate(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())));
						tasks.add(newTask);
						for(Task task:tasks) {
							if(task.getTaskNumber()==t.getTaskNumber()) {
								task.setRepetitionDate(null);
							}
						}
					}
				}
			}	
		}
		return newTask;
	}
	
	/**
     * this method returns the size of the Taskmanager
     * @return the size of the Taskmanager
     */
	public static int getSize() {
		return size;
	}
	
	/**
     * this method sets the size of the Taskmanager
     * @param size  the size of the Taskmanager
     */
	public static void setSize(int size) {
		Taskmanager.size = size;
	}
	
	
	public void saveToXML(File fileName) {		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);
			
			Element rootCategory = doc.createElement("Categories");
			rootElement.appendChild(rootCategory);
			
			Categorymanager catManager = Categorymanager.getInstance();
			for(Category c: catManager.getCategories()) {
	        	Element e = doc.createElement("category");
	            e.appendChild(doc.createTextNode(c.getCategory()));
	            rootCategory.appendChild(e);
			}
			
			Element rootContributors = doc.createElement("Contributors");
			rootElement.appendChild(rootContributors);
			Contributormanager conManager = Contributormanager.getInstance();
			for(Contributor c: conManager.getContributors()) {
				Element e = doc.createElement("contributor");
				e.appendChild(doc.createTextNode(c.getPerson()));
				rootContributors.appendChild(e);
			}
			
			String empty = "";
			
			Element rootTasks = doc.createElement("Tasks");
			rootElement.appendChild(rootTasks);
			
			System.out.println("Tasks Size: "+tasks.size());
			for(Task t:tasks) {
				if(t.getRepetitionDate() == null && t.getDueDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), empty, t.getContributors(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategories(), t.getAttachments(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasks(), empty));
				}else if(t.getDueDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), empty, t.getContributors(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategories(), t.getAttachments(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasks(), empty+t.getRepetitionDate().toString()));
				}else if(t.getRepetitionDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), empty, t.getContributors(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategories(), t.getAttachments(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasks(), empty));
				}else {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), t.getDueDate().toString(), t.getContributors(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategories(), t.getAttachments(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasks(), empty+t.getRepetitionDate().toString()));
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(fileName);
            
            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
		
	private static Node getTask(Document doc, String taskNumber, String taskDescription, String detailedTaskDescription, String dueDate, ObservableList<Contributor> contributors, String isRecurrent, String isWeekly, String isMonthly, String monthday, String weekday, String numberOfRepetitions, ObservableList<Category> categories, ObservableList<String> attachments, String creationDate, String isDone, ObservableList<Subtask> subtasks, String repetitionDate) {
        Element task = doc.createElement("Task");
        
        task.appendChild(getTaskElements(doc, "taskNumber", taskNumber));
        
        task.appendChild(getTaskElements(doc, "taskDescription", taskDescription));

        task.appendChild(getTaskElements(doc, "detailedTaskDescription", detailedTaskDescription));
        
        task.appendChild(getTaskElements(doc, "dueDate", dueDate));
        
        task.appendChild(getContributorsElements(doc, task, "contributor", contributors));
        
        task.appendChild(getTaskElements(doc, "isRecurrent", isRecurrent));
        
        task.appendChild(getTaskElements(doc, "isWeekly", isWeekly));
        
        task.appendChild(getTaskElements(doc, "isMonthly", isMonthly));
        
        task.appendChild(getTaskElements(doc, "monthday", monthday));
        
        task.appendChild(getTaskElements(doc, "weekday", weekday));
        
        task.appendChild(getTaskElements(doc, "numberOfRepititions", numberOfRepetitions));
        
        task.appendChild(getTaskElements(doc, "repetitionDate", repetitionDate));
        
        task.appendChild(getCategoriesElements(doc, "category", categories));
        
        task.appendChild(getAttachmentElements(doc, "attachment", attachments));
        
        task.appendChild(getTaskElements(doc, "creationDate", creationDate));
        
        task.appendChild(getTaskElements(doc, "isDone", isDone));
        
        task.appendChild(getSubtasksElements(doc, "subtask", subtasks));
        
        return task;
    }


    //utility method to create text node
    private static Node getTaskElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
  //utility method to create text node
    private static Node getContributorsElements(Document doc, Element element, String name, ObservableList<Contributor> contributors) {
    	Element e = null;
    	Element con = doc.createElement("Contributors");
    
    	for(Contributor c:contributors) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(c.getPerson()));
            con.appendChild(e);
    	}
        return con;
    }
    
    private static Node getCategoriesElements(Document doc, String name, ObservableList<Category> categories) {
    	Element e = null;
    	Element con = doc.createElement("Categories");
    
    	for(Category c:categories) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(c.getCategory()));
            con.appendChild(e);
    	}
        return con;
    }
    
    private static Node getAttachmentElements(Document doc, String name, ObservableList<String> attachments) {
    	Element e = null;
    	Element con = doc.createElement("Attachments");
    
    	for(String s:attachments) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(s));
            con.appendChild(e);
    	}
        return con;
    }
    
    private static Node getSubtasksElements(Document doc, String name, ObservableList<Subtask> subtasks) {
    	Element e = null;
    	Element con = doc.createElement("Subtasks");
    
    	for(Subtask s:subtasks) {
    		e = doc.createElement(name);
        	e.setAttribute("type", String.valueOf(s.isDone()));
        	e.appendChild(doc.createTextNode(s.getSubtask()));
            con.appendChild(e);
    	}
        return con;
    }
    
    public void readXML(File fileName) {
    	
    	File xmlFile =	fileName;
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
    	try {
    		dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(xmlFile);
    		doc.getDocumentElement().normalize();
    		NodeList nodeList = doc.getElementsByTagName("Task");
    		//now XML is loaded as Document in memory, lets convert it to Object List
    		for (int i = 0; i < nodeList.getLength(); i++) {
    			tasks.add(getTask(nodeList.item(i)));
    		}
    		
    		Categorymanager catManager = Categorymanager.getInstance();
    		NodeList nodeCatList = doc.getElementsByTagName("Categories");
    		for(int i = 0; i < nodeCatList.getLength(); i++) {
    			if(nodeCatList.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeCatList.item(i);
    				NodeList category = eElement.getElementsByTagName("category");
    				
    				for(int j = 0; j < category.getLength(); j++) {
    					Node node1 = category.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element c = (Element) node1;
    						catManager.addCategory(c.getTextContent());
    					}
    				}
    			}	
    		}
    		
    		Contributormanager conManager = Contributormanager.getInstance();
    		NodeList nodeConList = doc.getElementsByTagName("Contributors");
    		for(int i = 0; i < nodeConList.getLength(); i++) {
    			if(nodeConList.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeConList.item(i);
    				NodeList contributor = eElement.getElementsByTagName("contributor");
    				
    				for(int j = 0; j < contributor.getLength(); j++) {
    					Node node1 = contributor.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element c = (Element) node1;
    						conManager.addContributor(c.getTextContent());
    					}
    				}
    			}	
    		}
    	} catch (SAXException | ParserConfigurationException | IOException e1) {
    		e1.printStackTrace();
    	}
    }

    private static Task getTask(Node node) {
    	//XMLReaderDOM domReader = new XMLReaderDOM();
    	Task emp = new Task();
    	if (node.getNodeType() == Node.ELEMENT_NODE) {
    		Element element = (Element) node;
    		emp.setTaskDescription(getTagValue("taskDescription", element));
    		emp.setDetailedTaskDescription(getTagValue("detailedTaskDescription",element));
    		String date = getTagValue("dueDate",element);
    		if(!date.isEmpty()) {
    			emp.setDueDate(LocalDate.parse(date));
    		}
    		emp.setTaskNumber(Integer.parseInt(getTagValue("taskNumber",element)));
    		
    		ObservableList<Contributor> contri = FXCollections.observableArrayList();
    		NodeList nodeListCon = element.getElementsByTagName("Contributors");
    		for(int i = 0; i < nodeListCon.getLength(); i++) {
    			if(nodeListCon.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeListCon.item(i);
    				NodeList contributor = eElement.getElementsByTagName("contributor");
    				
    				for(int j = 0; j < contributor.getLength(); j++) {
    					Node node1 = contributor.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element con = (Element) node1;
    						contri.add(new Contributor(con.getTextContent()));
    					}
    				}
    			}	
    		}
    		emp.setContributors(contri);
    		
    		
    		ObservableList<Category> cat = FXCollections.observableArrayList();
    		NodeList nodeListCat = element.getElementsByTagName("Categories");
    		for(int i = 0; i < nodeListCat.getLength(); i++) {
    			if(nodeListCat.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeListCat.item(i);
    				NodeList category = eElement.getElementsByTagName("category");
    				
    				for(int j = 0; j < category.getLength(); j++) {
    					Node node1 = category.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element c = (Element) node1;
    						cat.add(new Category(c.getTextContent()));
    					}
    				}
    			}	
    		}
    		emp.setCategories(cat);
    		
    		ObservableList<String> att = FXCollections.observableArrayList();
    		NodeList nodeListAtt = element.getElementsByTagName("Attachments");
    		for(int i = 0; i < nodeListAtt.getLength(); i++) {
    			if(nodeListAtt.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeListAtt.item(i);
    				NodeList attachment = eElement.getElementsByTagName("attachment");
    				
    				for(int j = 0; j < attachment.getLength(); j++) {
    					Node node1 = attachment.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element a = (Element) node1;
    						att.add(a.getTextContent());
    					}
    				}
    			}	
    		}
    		emp.setAttachments(att);
    		
    		
    		ObservableList<Subtask> sub = FXCollections.observableArrayList();
    		NodeList nodeListSub = element.getElementsByTagName("Subtasks");
    		for(int i = 0; i < nodeListSub.getLength(); i++) {
    			if(nodeListSub.item(i).getNodeType()==Node.ELEMENT_NODE) {
    				Element eElement = (Element) nodeListSub.item(i);
    				NodeList subtask = eElement.getElementsByTagName("subtask");
    				
    				for(int j = 0; j < subtask.getLength(); j++) {
    					Node node1 = subtask.item(j);
    					
    					if(node1.getNodeType() == node1.ELEMENT_NODE) {
    						Element s = (Element) node1;
    						Subtask newSubtask = new Subtask(s.getTextContent());
    						String example = s.getAttribute("type");
    						newSubtask.setDone("true".equals(example));
    						sub.add(newSubtask);
    					}
    				}
    			}	
    		}
    		emp.setSubtasks(sub);
    		
    		String dummy = getTagValue("isRecurrent", element);
    		emp.setRecurrent("true".equals(dummy));
    		dummy = getTagValue("isWeekly", element);
    		emp.setWeekly("true".equals(dummy));
    		dummy = getTagValue("isMonthly", element);
    		emp.setMonthly("true".equals(dummy));
    		emp.setMonthday(Integer.parseInt(getTagValue("monthday", element)));
    		emp.setWeekday(handleWeekday(getTagValue("weekday",element)));
    		emp.setNumberOfRepetitions(Integer.parseInt(getTagValue("numberOfRepititions", element)));
    		date = getTagValue("repetitionDate", element);
    		if(!date.isEmpty()) {
    			emp.setRepetitionDate(LocalDate.parse(date));
    		}
    		emp.setCreationDate(LocalDate.parse(getTagValue("creationDate",element)));
    		dummy = getTagValue("isDone", element);
    		emp.setDone("true".equals(dummy));
    	}

    	return emp;
    }
    
    
    public static Weekday handleWeekday(String s) {
    	if(s.equals("MONDAY")) {
    		return Weekday.MONDAY;
    	}else if(s.equals("TUESDAY")) {
    		return Weekday.TUESDAY;
    	}else if(s.equals("WEDNESDAY")) {
    		return Weekday.WEDNESDAY;
    	}else if(s.equals("THURSDAY")) {
    		return Weekday.THURSDAY;
    	}else if(s.equals("FRIDAY")) {
    		return Weekday.FRIDAY;
    	}else if(s.equals("SATURDAY")) {
    		return Weekday.SATURDAY;
    	}else if(s.equals("SUNDAY")) {
    		return Weekday.SUNDAY;
    	}else {
    		return null;
    	}
    }

    private static String getTagValue(String tag, Element element) {
    	if(element.getElementsByTagName(tag).item(0).getChildNodes() == null) {
    		return "";
    	}
    	NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    	if(nodeList.item(0) == null) {
    		return "";
    	}
    	Node node = (Node) nodeList.item(0);
    	return node.getNodeValue();
    }
}
