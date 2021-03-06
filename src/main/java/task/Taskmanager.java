package task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import calendar.Weekday;
import category.Category;
import category.Categorymanager;
import contributor.Contributor;
import contributor.Contributormanager;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class which represents the existing tasks
 * @author Mara
 */
public final class Taskmanager {
	
	private static Taskmanager instance;
	
	private ObservableList<Task> tasks;
	private static int size;
	
	private static final String CATEGORIES = "Categories";
	private static final String CATEGORY = "category";
	private static final String CONTRIBUTORS = "Contributors";
	private static final String CONTRIBUTOR = "contributor";
	private static final String ATTACHMENTS = "Attachments";
	private static final String SUBTASKS = "Subtasks";
	
	private static final Logger LOGGER = Logger.getLogger(Taskmanager.class.getName());
	
	
	/**
     * private constructor which initializes the Taskmanager
     */
	private Taskmanager() {
		this.tasks = FXCollections.observableArrayList();
	}
		
	/**
     * this method returns the single instance of the Taskmanager
     * @return the single instance of the Taskmanager
     */
	public static synchronized Taskmanager getInstance() {
		if(instance == null) {
			instance = new Taskmanager();
		}
		return instance;
	}
	
	/**
     * this method returns a list of the tasks
     * @return list of the tasks
     */
	public ObservableList<Task> getTasks() {
		return FXCollections.observableList(tasks);
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
		changeSize(true);
		return true;
	}
	
	/**
	 * this method changes the size
	 * @param b to reduce or increase the size
	 */
	private static void changeSize(boolean b) {
		if(b) {
			size++;
		}else {
			size--;
		}
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
			changeSize(false);
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
		for(Task t:tasks) {
			for(Category c:t.getCategoriesList()) {
				if(c.getCategory().equals(category)) {
					index = t.getCategoriesList().indexOf(c);
				}
			}
			if(index!=-1) {
				t.getCategoriesList().remove(index);
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
				
				if(t.getNumberOfRepetitions()>0 && LocalDate.now().isAfter(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()))){
					newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null , t.getContributorsList(), t.getCategoriesList(), t.getSubtasksList(), t.getAttachmentsList(), t.getMonthday(), (t.getNumberOfRepetitions()-1), null);
					newTask.setCreationDate(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()));
					tasks.add(newTask);
					t.setNumberOfRepetitions(0);
				}else if(t.getRepetitionDate()!=null && LocalDate.now().isAfter(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()))) {
					newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null , t.getContributorsList(), t.getCategoriesList(), t.getSubtasksList(), t.getAttachmentsList(), t.getMonthday(), 0, t.getRepetitionDate());
					newTask.setCreationDate(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()));
					tasks.add(newTask);
					t.setRepetitionDate(null);
				}
			}else if(t.isWeekly()) {
				if(t.getNumberOfRepetitions()>0 && LocalDate.now().isAfter(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())))){
					newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null, t.getContributorsList(), t.getCategoriesList(), t.getSubtasksList(), t.getAttachmentsList(), t.getWeekday(), (t.getNumberOfRepetitions()-1), null);
					newTask.setCreationDate(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())));
					tasks.add(newTask);
					t.setNumberOfRepetitions(0);
				}else if(t.getRepetitionDate()!=null && LocalDate.now().isAfter(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())))) {
					newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null, t.getContributorsList(), t.getCategoriesList(), t.getSubtasksList(), t.getAttachmentsList(), t.getWeekday(), 0 , t.getRepetitionDate());
					newTask.setCreationDate(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())));
					tasks.add(newTask);
					t.setRepetitionDate(null);
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
	
	/**
	 * this method clears the tasks
	 */
	public void setEmpty() {
		tasks.clear();
	}
	
	/**
	 * this method saves the tasks to XML
	 * @param fileName
	 */
	public void saveToXML(File fileName) {	
		if(fileName == null) {
			LOGGER.log(Level.SEVERE, "File does not exist");
		}
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
			DocumentBuilder dBuilder;
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element rootElement = doc.createElement("Data");
			doc.appendChild(rootElement);
			
			Element rootCategory = doc.createElement(CATEGORIES);
			rootElement.appendChild(rootCategory);
			
			Categorymanager catManager = Categorymanager.getInstance();
			for(Category c: catManager.getCategories()) {
	        	Element e = doc.createElement(CATEGORY);
	            e.appendChild(doc.createTextNode(c.getCategory()));
	            rootCategory.appendChild(e);
			}
			
			Element rootContributors = doc.createElement(CONTRIBUTORS);
			rootElement.appendChild(rootContributors);
			Contributormanager conManager = Contributormanager.getInstance();
			for(Contributor c: conManager.getContributors()) {
				Element e = doc.createElement(CONTRIBUTOR);
				e.appendChild(doc.createTextNode(c.getPerson()));
				rootContributors.appendChild(e);
			}
			
			String empty = "";
			
			Element rootTasks = doc.createElement("Tasks");
			rootElement.appendChild(rootTasks);
			
			for(Task t:tasks) {
				if(t.getRepetitionDate() == null && t.getDueDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), empty, t.getContributorsList(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategoriesList(), t.getAttachmentsList(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasksList(), empty));
				}else if(t.getDueDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), empty, t.getContributorsList(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategoriesList(), t.getAttachmentsList(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasksList(), empty+t.getRepetitionDate().toString()));
				}else if(t.getRepetitionDate() == null) {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), t.getDueDate().toString(), t.getContributorsList(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategoriesList(), t.getAttachmentsList(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasksList(), empty));
				}else {
					rootTasks.appendChild(getTask(doc, empty+t.getTaskNumber(), t.getTaskDescription(), empty+t.getDetailedTaskDescription(), t.getDueDate().toString(), t.getContributorsList(), String.valueOf(t.isRecurrent()), String.valueOf(t.isWeekly()), String.valueOf(t.isMonthly()), empty+t.getMonthday(), empty+t.getWeekday(), empty+t.getNumberOfRepetitions(), t.getCategoriesList(), t.getAttachmentsList(), t.getCreationDate().toString(), String.valueOf(t.isDone()), t.getSubtasksList(), empty+t.getRepetitionDate().toString()));
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
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

        } catch (ParserConfigurationException | TransformerException e) {
        	LOGGER.log(Level.SEVERE, "Exception occured (Save To XML)", e);
        }
    }
	
	/**
	 * this method creates the XML structure	
	 * @param doc
	 * @param taskNumber
	 * @param taskDescription
	 * @param detailedTaskDescription
	 * @param dueDate
	 * @param contributors
	 * @param isRecurrent
	 * @param isWeekly
	 * @param isMonthly
	 * @param monthday
	 * @param weekday
	 * @param numberOfRepetitions
	 * @param categories
	 * @param attachments
	 * @param creationDate
	 * @param isDone
	 * @param subtasks
	 * @param repetitionDate
	 * @return
	 */
	private static Node getTask(Document doc, String taskNumber, String taskDescription, String detailedTaskDescription, String dueDate, ObservableList<Contributor> contributors, String isRecurrent, String isWeekly, String isMonthly, String monthday, String weekday, String numberOfRepetitions, ObservableList<Category> categories, ObservableList<String> attachments, String creationDate, String isDone, ObservableList<Subtask> subtasks, String repetitionDate) {
        Element task = doc.createElement("Task");
        
        task.appendChild(getTaskElements(doc, "taskNumber", taskNumber));
        
        task.appendChild(getTaskElements(doc, "taskDescription", taskDescription));

        task.appendChild(getTaskElements(doc, "detailedTaskDescription", detailedTaskDescription));
        
        task.appendChild(getTaskElements(doc, "dueDate", dueDate));
        
        task.appendChild(getContributorsElements(doc, CONTRIBUTOR , contributors));
        
        task.appendChild(getTaskElements(doc, "isRecurrent", isRecurrent));
        
        task.appendChild(getTaskElements(doc, "isWeekly", isWeekly));
        
        task.appendChild(getTaskElements(doc, "isMonthly", isMonthly));
        
        task.appendChild(getTaskElements(doc, "monthday", monthday));
        
        task.appendChild(getTaskElements(doc, "weekday", weekday));
        
        task.appendChild(getTaskElements(doc, "numberOfRepititions", numberOfRepetitions));
        
        task.appendChild(getTaskElements(doc, "repetitionDate", repetitionDate));
        
        task.appendChild(getCategoriesElements(doc, CATEGORY , categories));
        
        task.appendChild(getAttachmentElements(doc, "attachment", attachments));
        
        task.appendChild(getTaskElements(doc, "creationDate", creationDate));
        
        task.appendChild(getTaskElements(doc, "isDone", isDone));
        
        task.appendChild(getSubtasksElements(doc, "subtask", subtasks));
        
        return task;
    }

	/**
	 * utility method to create text node for tasks
	 * @param doc
	 * @param name
	 * @param value
	 * @return
	 */
    private static Node getTaskElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
    /**
     * utility method to create text node for contributors
     * @param doc
     * @param name
     * @param contributors
     * @return
     */
    private static Node getContributorsElements(Document doc, String name, ObservableList<Contributor> contributors) {
    	Element e = null;
    	Element con = doc.createElement(CONTRIBUTORS);
    
    	for(Contributor c:contributors) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(c.getPerson()));
            con.appendChild(e);
    	}
        return con;
    }
    
    /**
     * utility method to create text node for categories
     * @param doc
     * @param name
     * @param categories
     * @return
     */
    private static Node getCategoriesElements(Document doc, String name, ObservableList<Category> categories) {
    	Element e = null;
    	Element con = doc.createElement(CATEGORIES);
    
    	for(Category c:categories) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(c.getCategory()));
            con.appendChild(e);
    	}
        return con;
    }
    
    /**
     * utility method to create text node for attachments
     * @param doc
     * @param name
     * @param attachments
     * @return
     */
    private static Node getAttachmentElements(Document doc, String name, ObservableList<String> attachments) {
    	Element e = null;
    	Element con = doc.createElement(ATTACHMENTS);
    
    	for(String s:attachments) {
    		e = doc.createElement(name);
            e.appendChild(doc.createTextNode(s));
            con.appendChild(e);
    	}
        return con;
    }
    
    /**
     * utility method to create text node for subtasks
     * @param doc
     * @param name
     * @param subtasks
     * @return
     */
    private static Node getSubtasksElements(Document doc, String name, ObservableList<Subtask> subtasks) {
    	Element e = null;
    	Element con = doc.createElement(SUBTASKS);
    
    	for(Subtask s:subtasks) {
    		e = doc.createElement(name);
        	e.setAttribute("type", String.valueOf(s.isDone()));
        	e.appendChild(doc.createTextNode(s.getSubtask()));
            con.appendChild(e);
    	}
        return con;
    }
    
    /**
     * this method reads a XML document
     * @param fileName
     * @throws ParserConfigurationException
     */
    public void readXML(File fileName) throws ParserConfigurationException {
    	
    	File xmlFile =	fileName;
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
    	DocumentBuilder dBuilder;
    	try {
    		dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(xmlFile);
    		doc.getDocumentElement().normalize();
    		NodeList nodeList = doc.getElementsByTagName("Task");
    		//now XML is loaded as Document in memory, lets convert it to Object List
    		for (int i = 0; i < nodeList.getLength(); i++) {
    			changeSize(true);
    			Task t = getTask(nodeList.item(i));
    			t.setTaskNumber(size);
    			tasks.add(t);
    		}
    		
    		Categorymanager catManager = Categorymanager.getInstance();
    		NodeList nodeCatList = doc.getElementsByTagName(CATEGORIES);
    		if(nodeCatList.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeCatList.item(0);
    			NodeList category = eElement.getElementsByTagName(CATEGORY);
    			
    			for(int j = 0; j < category.getLength(); j++) {
    				Node node1 = category.item(j);
    				Element c = (Element) node1;
					catManager.addCategory(c.getTextContent());
    			}
    		}	
    		
    		Contributormanager conManager = Contributormanager.getInstance();
    		NodeList nodeConList = doc.getElementsByTagName(CONTRIBUTORS);
    		if(nodeConList.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeConList.item(0);
    			NodeList contributor = eElement.getElementsByTagName(CONTRIBUTOR);
    				
    			for(int j = 0; j < contributor.getLength(); j++) {
    				Node node1 = contributor.item(j);
    				Element c = (Element) node1;
					conManager.addContributor(c.getTextContent());
    			}
    		}	
    		
    	} catch (SAXException | ParserConfigurationException | IOException e1) {
    		LOGGER.log(Level.SEVERE, "Exception occured (read XML)", e1);
    	}
    }
    
    /**
     * this method turns a node into a task
     * @param node
     * @return
     */
    private static Task getTask(Node node) {
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
    		NodeList nodeListCon = element.getElementsByTagName(CONTRIBUTORS);
    		if(nodeListCon.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeListCon.item(0);
    			NodeList contributor = eElement.getElementsByTagName(CONTRIBUTOR);
    				
    			for(int j = 0; j < contributor.getLength(); j++) {
    				Node node1 = contributor.item(j);
    				Element con = (Element) node1;
					contri.add(new Contributor(con.getTextContent()));
    			}
    		}
    		
    		emp.setContributors(contri);
    		
    		ObservableList<Category> cat = FXCollections.observableArrayList();
    		NodeList nodeListCat = element.getElementsByTagName(CATEGORIES);
    		if(nodeListCat.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeListCat.item(0);
    			NodeList category = eElement.getElementsByTagName(CATEGORY);
    				
    			for(int j = 0; j < category.getLength(); j++) {
    				Node node1 = category.item(j);
    				Element c = (Element) node1;
					cat.add(new Category(c.getTextContent()));
    			}	
    		}
    		
    		emp.setCategories(cat);
    		
    		ObservableList<String> att = FXCollections.observableArrayList();
    		NodeList nodeListAtt = element.getElementsByTagName(ATTACHMENTS);
    		if(nodeListAtt.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeListAtt.item(0);
    			NodeList attachment = eElement.getElementsByTagName("attachment");
    				
    			for(int j = 0; j < attachment.getLength(); j++) {
    				Node node1 = attachment.item(j);
    				Element a = (Element) node1;
					att.add(a.getTextContent());
    			}	
    		}
    		
    		emp.setAttachments(att);
    		
    		ObservableList<Subtask> sub = FXCollections.observableArrayList();
    		NodeList nodeListSub = element.getElementsByTagName(SUBTASKS);
    		if(nodeListSub.item(0).getNodeType()==Node.ELEMENT_NODE) {
    			Element eElement = (Element) nodeListSub.item(0);
    			NodeList subtask = eElement.getElementsByTagName("subtask");
    				
    			for(int j = 0; j < subtask.getLength(); j++) {
    				Node node1 = subtask.item(j);
    				Element s = (Element) node1;
					Subtask newSubtask = new Subtask(s.getTextContent());
					String example = s.getAttribute("type");
					newSubtask.setDone("true".equals(example));
					sub.add(newSubtask);
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
    
    /**
     * this method returns a String s to a Weekday
     * @param s
     * @return
     */
    public static Weekday handleWeekday(String s) {  	
    	Weekday w = null;
    	switch(s) {
    		case "MONDAY":
    			w = Weekday.MONDAY;
    			break;
    		case "TUESDAY":
    			w = Weekday.TUESDAY;
    			break;
    		case "WEDNESDAY":
    			w = Weekday.WEDNESDAY;
    			break;
    		case "THURSDAY":
    			w = Weekday.THURSDAY;
    			break;
    		case "FRIDAY":
    			w = Weekday.FRIDAY;
    			break;
    		case "SATURDAY":
    			w = Weekday.SATURDAY;
    			break;
    		case "SUNDAY":
    			w = Weekday.SUNDAY;
    			break;
    		default:
                break;
    	}
    	return w;
    }
    
    /**
     * this methods returns the value of a Tag
     * @param tag
     * @param element
     * @return
     */
    private static String getTagValue(String tag, Element element) {
    	if(element.getElementsByTagName(tag).item(0).getChildNodes() == null) {
    		return "";
    	}
    	NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
    	if(nodeList.item(0) == null) {
    		return "";
    	}
    	Node node = nodeList.item(0);
    	return node.getNodeValue();
    }
    
    /**
     * this method saves the task to CSV
     * @param fileName
     * @return
     * @throws IOException
     */
    public boolean saveToCsv(File fileName) throws IOException{		
		
		try (FileWriter csvWriter = new FileWriter(fileName)){
			//Headers of Csv file "," is use to make new column
			csvWriter.append("Task No");
			csvWriter.append(";");
			csvWriter.append("Description");
			csvWriter.append(";");
			csvWriter.append("Detail Description");
			csvWriter.append(";");
			csvWriter.append("Due Date");
			csvWriter.append(";");
			csvWriter.append(CONTRIBUTORS);
			csvWriter.append(";");
			csvWriter.append(CATEGORIES);
			csvWriter.append(";");
			csvWriter.append(SUBTASKS);
			csvWriter.append(";");
			csvWriter.append(ATTACHMENTS);
			csvWriter.append(";");
			csvWriter.append("Recurrent");
			csvWriter.append(";");
			csvWriter.append("Done");
			csvWriter.append("\n");				
			//Adding data in CSV file
			for (Task t:tasks) {
				BooleanProperty done = t.getDone();
				String doneVal = String.valueOf(done.getValue());
				
				Boolean recurrent = t.isRecurrent();
				String recurrentVal = String.valueOf(recurrent);
				
				csvWriter.append(String.join(";", t.getTaskNumber()+";"+t.getTaskDescription()+";"+t.getDetailedTaskDescription()+";"+t.getDueDate()
				+ ";" + t.getContributors() + ";" + t.getCategories() + ";" + t.getSubtasks() + ";" + t.getAttachments() + ";" + recurrentVal + ";" + doneVal));
			    csvWriter.append("\n");
			}

			csvWriter.flush();
			return true;

        } catch (Exception e) {
        	LOGGER.log(Level.SEVERE, "Exception occured (Save To CSV)", e);
        }
		return false;
    }
    
}
