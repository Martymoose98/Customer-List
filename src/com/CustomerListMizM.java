package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Martin
 *
 *         Represents a postal code
 *
 */
class PostalCode
{
	public static final int POSTAL_CODE_MAX_LENGTH = 7;
	private char[] postalcode;

	/**
	 * Constructor
	 * 
	 * @param postalcode
	 *            a string representing a postal code
	 */
	public PostalCode(String postalcode)
	{
		if (postalcode == null)
			return;

		this.postalcode = (validate(postalcode.toCharArray())) ? postalcode.toCharArray() : null;
	}

	/**
	 * Constructor
	 * 
	 * @param postalcode
	 *            a character array representing a postal code
	 */
	public PostalCode(char[] postalcode)
	{
		if (postalcode == null)
			return;

		this.postalcode = (validate(postalcode)) ? postalcode : null;
	}

	/**
	 * Converts the postal code to string format
	 * 
	 * @return the postal code else null
	 */
	public String getPostalCode()
	{
		return (this.postalcode != null) ? new String(this.postalcode) : null;
	}

	/**
	 * Checks if the postal code is valid then returns the result
	 * 
	 * @return true if the postal code is valid else false
	 */
	private boolean validate()
	{
		return (this.postalcode.length == POSTAL_CODE_MAX_LENGTH && checkIfCharIsAlpha(this.postalcode[0]) && checkIfCharIsNumeric(this.postalcode[1]) && checkIfCharIsAlpha(this.postalcode[2]) &&
				this.postalcode[3] == 0x20 && checkIfCharIsNumeric(this.postalcode[4]) && checkIfCharIsAlpha(this.postalcode[5]) && checkIfCharIsNumeric(this.postalcode[6]));
	}

	/**
	 * Checks if the postal code is valid then returns the result
	 * 
	 * @param postalcode
	 *            the postal code to be test for it's validity
	 * 
	 * @return true if the postal code is valid else false
	 */
	private boolean validate(char[] postalcode)
	{
		return (postalcode.length == POSTAL_CODE_MAX_LENGTH && checkIfCharIsAlpha(postalcode[0]) && checkIfCharIsNumeric(postalcode[1]) && checkIfCharIsAlpha(postalcode[2]) &&
				postalcode[3] == 0x20 && checkIfCharIsNumeric(postalcode[4]) && checkIfCharIsAlpha(postalcode[5]) && checkIfCharIsNumeric(postalcode[6]));
	}

	/**
	 * Checks if the postal code is valid then returns the result
	 * 
	 * @param postalcode
	 *            the postal code to be test for it's validity
	 * 
	 * @return true if the postal code is valid else false
	 */
	public static boolean checkIfValid(char[] postalcode)
	{
		return new PostalCode(postalcode).validate();
	}

	/**
	 * Checks if the character is part of the alphabet
	 * 
	 * @param c
	 *            the character to be tested
	 * 
	 * @return true if the character is part of the English alphabet else false
	 */
	private boolean checkIfCharIsAlpha(char c)
	{
		return !((byte) c < 0x41 || (byte) c > 0x7A || ((byte) c > 0x5A && (byte) c < 0x61));
	}

	/**
	 * Checks if the character is a number
	 * 
	 * @param c
	 *            the character to be tested
	 * 
	 * @return true if the character is a number else false
	 */
	private boolean checkIfCharIsNumeric(char c)
	{
		return ((byte) c > 0x29 && (byte) c < 0x3A);
	}

	/**
	 * Overridden toString Method
	 */
	@Override
	public String toString()
	{
		return getPostalCode();
	}
}

/**
 * 
 * @author Martin
 *
 *         Represents a customer; holds all the data of a customer
 *
 */
class Customer
{
	public UUID uuid;
	public String name;
	public String address;
	public String city;
	public String province;
	public PostalCode postalcode;

	/**
	 * This constructor should only be called by load
	 * 
	 * @param uuid
	 *            an universally unique identifier
	 * 
	 * @param name
	 *            name of the customer
	 * 
	 * @param address
	 *            address of the customer
	 * 
	 * @param city
	 *            city where the customer resides
	 * 
	 * @param provice
	 *            province where the customer resides
	 * 
	 * @param postalcode
	 *            postal code of the customer in string form
	 */
	protected Customer(UUID uuid, String name, String address, String city, String provice, String postalcode)
	{
		assert (uuid != null);

		this.uuid = uuid;
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = provice;
		this.postalcode = new PostalCode(postalcode);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the customer
	 * 
	 * @param address
	 *            address of the customer
	 * 
	 * @param city
	 *            city where the customer resides
	 * 
	 * @param provice
	 *            province where the customer resides
	 * 
	 * @param postalcode
	 *            postal code of the customer in object form
	 */
	public Customer(String name, String address, String city, String provice, PostalCode postalcode)
	{
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = provice;
		this.postalcode = postalcode;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the customer
	 * 
	 * @param address
	 *            address of the customer
	 * 
	 * @param city
	 *            city where the customer resides
	 * 
	 * @param provice
	 *            province where the customer resides
	 * 
	 * @param postalcode
	 *            postal code of the customer in string form
	 */
	public Customer(String name, String address, String city, String provice, String postalcode)
	{
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = provice;
		this.postalcode = new PostalCode(postalcode);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the customer
	 * 
	 * @param address
	 *            address of the customer
	 * 
	 * @param city
	 *            city where the customer resides
	 * 
	 * @param provice
	 *            province where the customer resides
	 * 
	 * @param postalcode
	 *            postal code of the customer in character array form
	 */
	public Customer(String name, String address, String city, String provice, char[] postalcode)
	{
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = provice;
		this.postalcode = new PostalCode(postalcode);
	}

	/**
	 * Overridden toString method
	 */
	public String toString()
	{
		return String.format("Name: %s\nAddress: %s\nCity: %s\nProvince: %s\nPostal Code: %s\n\n", this.name, this.address, this.city, this.province, this.postalcode.getPostalCode());
	}
}

/**
 * 
 * @author Martin
 *
 *         Customer database holds all of the customers in a map for easy data fetching
 *
 */
class CustomerDatabase
{
	/**
	 * Singletons
	 */
	private static TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private static Transformer transformer = null;
	private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder builder = null;

	private volatile HashMap<String, Customer> customers;
	private volatile File file;

	/**
	 * Constructor
	 * 
	 * @param filename
	 *            the relative XML file name
	 * @param bLoad
	 *            will the database be loaded on construction of the object
	 */
	public CustomerDatabase(String filename, boolean bLoad)
	{
		if (builder == null)
		{
			try
			{
				builder = documentBuilderFactory.newDocumentBuilder();
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}

		if (transformer == null)
		{
			try
			{
				transformer = transformerFactory.newTransformer();
			}
			catch (TransformerConfigurationException e)
			{
				e.printStackTrace();
			}
		}

		this.file = new File(filename);
		this.customers = new HashMap<String, Customer>();

		if (bLoad)
			load();
	}

	/**
	 * Constructor
	 * 
	 * @param filename
	 *            a File object that represents the XML file
	 * 
	 * @param bLoad
	 *            will the database be loaded on construction of the object
	 */
	public CustomerDatabase(File file, boolean bLoad)
	{
		if (builder == null)
		{
			try
			{
				builder = documentBuilderFactory.newDocumentBuilder();
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			}
		}

		if (transformer == null)
		{
			try
			{
				transformer = transformerFactory.newTransformer();
			}
			catch (TransformerConfigurationException e)
			{
				e.printStackTrace();
			}
		}

		this.file = file;
		this.customers = new HashMap<String, Customer>();

		if (bLoad)
			load();
	}

	/**
	 * Clears the customer map of all entries
	 */
	public void clear()
	{
		this.customers.clear();
	}

	/**
	 * Pushes a new entry onto the customer map using the customer's name as the key
	 * 
	 * @param customer
	 *            the new customer
	 */
	public void push(Customer customer)
	{
		this.customers.put(customer.name, customer);
	}

	/**
	 * Removes the customer specified
	 * 
	 * @param name
	 *            the name of the customer to be removed
	 * 
	 * @return true if the value was removed
	 */
	public boolean pop(String name)
	{
		return this.customers.remove(name, find(name));
	}

	/**
	 * Removes the last customer in the map
	 */
	public void popBack()
	{
		Iterator<Entry<String, Customer>> it = this.customers.entrySet().iterator();
		Entry<String, Customer> pair = null;

		while (it.hasNext())
			pair = it.next();

		this.customers.remove(pair.getKey(), pair.getValue());
	}

	/**
	 * Returns the customer specified
	 * 
	 * @param name
	 *            the name of the customer to find
	 * 
	 * @return the customer queried else null
	 */
	public Customer find(String name)
	{
		return this.customers.get(name);
	}

	/**
	 * Returns a vector populated with customer objects from the database
	 * 
	 * @return a vector populated with customer objects
	 */
	public Vector<Customer> getCustomerVector()
	{
		Collection<Customer> customers = this.customers.values();
		Vector<Customer> vCustomers = new Vector<Customer>(this.customers.size());

		customers.forEach((customer) -> vCustomers.add(customer));

		return vCustomers;
	}

	/**
	 * Sets the database map values
	 * 
	 * @param values
	 *            a vector populated with customer objects
	 */
	public void setCustomerVector(Vector<Customer> values)
	{
		this.customers.clear();

		for (Customer customer : values)
			this.customers.put(customer.name, customer);
	}

	/**
	 * Saves the current database to the XML file
	 */
	public void save()
	{
		Document xmlfile = builder.newDocument();

		// append the root element
		Element root = xmlfile.createElement("Customers");
		xmlfile.appendChild(root);

		Iterator<Entry<String, Customer>> it = this.customers.entrySet().iterator();

		while (it.hasNext())
		{
			Entry<String, Customer> pair = it.next();
			Customer current = pair.getValue();
			Element customer = xmlfile.createElement(current.name.replaceAll(" ", "_")); // xml doesn't like spaces for elements ;)

			Attr uuid = xmlfile.createAttribute("UUID");
			uuid.setValue(current.uuid.toString());
			customer.setAttributeNode(uuid);

			// add name
			Element name = xmlfile.createElement("Name");
			name.appendChild(xmlfile.createTextNode(current.name));
			customer.appendChild(name);

			// add address
			Element address = xmlfile.createElement("Address");
			address.appendChild(xmlfile.createTextNode(current.address));
			customer.appendChild(address);

			// add city
			Element city = xmlfile.createElement("City");
			city.appendChild(xmlfile.createTextNode(current.city));
			customer.appendChild(city);

			// add province
			Element province = xmlfile.createElement("Province");
			province.appendChild(xmlfile.createTextNode(current.province));
			customer.appendChild(province);

			// add postal code
			Element postalcode = xmlfile.createElement("PostalCode");
			postalcode.appendChild(xmlfile.createTextNode(current.postalcode.toString()));
			customer.appendChild(postalcode);

			root.appendChild(customer);
		}

		// write contents to a file
		try
		{
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // enable formatting
			transformer.transform(new DOMSource(xmlfile), new StreamResult(this.file));
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
		}

		System.out.println(this.file.getAbsolutePath() + " Saved!");
	}

	/**
	 * Loads the database stored on the XML file
	 */
	public void load()
	{
		Document xmlfile = null;

		this.customers.clear();

		if (!this.file.exists())
			return;

		if (!this.file.canRead())
			return;

		try
		{
			xmlfile = builder.parse(file);
		}
		catch (SAXException | IOException e)
		{
			e.printStackTrace();
			return;
		}

		xmlfile.normalize();

		NodeList list = xmlfile.getElementsByTagName("Customers");

		for (int i = 0; i < list.getLength(); ++i)
		{
			Node node = list.item(i);

			if (node.hasChildNodes())
			{
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;

					if (element.getNodeName().equals("Customers"))
					{
						NodeList children = element.getChildNodes();

						for (int j = 0; j < children.getLength(); ++j)
						{
							Node child = children.item(j);

							if (child.getNodeType() == Node.ELEMENT_NODE)
							{
								Element elementChild = (Element) child;
								NamedNodeMap attributes = elementChild.getAttributes();
								Attr uuidAttribute = (Attr) attributes.getNamedItem("UUID");

								UUID uuid = UUID.fromString(uuidAttribute.getValue());
								String name = getItem(elementChild, "Name");
								String address = getItem(elementChild, "Address");
								String city = getItem(elementChild, "City");
								String province = getItem(elementChild, "Province");
								String postalcode = getItem(elementChild, "PostalCode");

								push(new Customer(uuid, name, address, city, province, postalcode));
							}
						}
					}
				}
			}
		}

		System.out.println(this.file.getAbsolutePath() + " Loaded!");
	}

	/**
	 * Wrapper method for: String getItem(Element node, String elementName, int index = 0) The index is always zero (most cases)
	 * 
	 * @param node
	 *            the node element (Parent)
	 * 
	 * @param elementName
	 *            the element name to find
	 * 
	 * @return a string representation of the value
	 */
	private String getItem(Element node, String elementName)
	{
		return getItem(node, elementName, 0);
	}

	/**
	 * Helper method for obtaining element child data
	 * 
	 * @param node
	 *            the node element (Parent)
	 * 
	 * @param elementName
	 *            the element name to find
	 * 
	 * @param index
	 *            the index of the value
	 * 
	 * @return a string representation of the value
	 */
	private String getItem(Element node, String elementName, int index)
	{
		NodeList list = node.getElementsByTagName(elementName);
		return list.item(index).getTextContent();
	}

	/**
	 * Overridden toString method
	 */
	public String toString()
	{
		String ret = new String();

		for (Customer customer : this.customers.values())
			ret += customer.toString();

		return ret;
	}

	/**
	 * Converts a vector of vectors with customer data into a vector of Customer objects
	 * 
	 * @param data
	 *            a vector of vectors with customer data
	 * 
	 * @return a vector of Customer objects
	 */
	public static Vector<Customer> convertCustomerDataToCustomerVector(Vector<Vector<String>> data)
	{
		Vector<Customer> vCustomers = new Vector<Customer>(data.size());

		for (Vector<String> customerData : data)
		{
			vCustomers.add(new Customer(customerData.get(Client.NAME_COLUMN_INDEX), customerData.get(Client.ADDRESS_COLUMN_INDEX),
					customerData.get(Client.CITY_COLUMN_INDEX), customerData.get(Client.PROVINCE_COLUMN_INDEX), customerData.get(Client.POSTAL_CODE_COLUMN_INDEX)));
		}

		return vCustomers;
	}
}

/**
 * 
 * @author Martin
 *
 *         Helper class to handle input
 *
 * @param <I>
 *            the caller instance of any class that extends client or client itself
 */
class Input<I extends Client> implements ActionListener, TableModelListener
{

	public Consumer<I> callback;
	public I instance;

	/**
	 * 
	 * @param callback
	 *            a callback with the instance as the parameter
	 * 
	 * @param instance
	 *            the instance of the class that extends client or client itself
	 */
	public Input(Consumer<I> callback, I instance)
	{
		this.callback = callback;
		this.instance = instance;
	}

	/**
	 * Overridden actionPerformed that invokes the callback
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		callback.accept(this.instance);
	}

	/**
	 * Overridden tableChanged that invokes the callback
	 */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		callback.accept(this.instance);
	}
}

/*
 * 
 */
class Client extends JFrame
{
	/**
	 * Serial version unique identifier
	 */
	private static final long serialVersionUID = 648057497937813320L;

	/**
	 * Width and height constants
	 */
	private static final int WIDTH = 750;
	private static final int HEIGHT = 400;

	/**
	 * Constant customer data categories list
	 */
	private static final Vector<String> CUSTOMER_DATA_CLASSLIST = new Vector<String>(Arrays.asList("Name", "Address", "City", "Province", "PostalCode"));

	/**
	 * the null value placeholder for new customers added to the JTable
	 */
	public static final String NULL_VALUE = new String();

	/**
	 * Constant column indices
	 */
	public static final int NAME_COLUMN_INDEX = 0;
	public static final int ADDRESS_COLUMN_INDEX = 1;
	public static final int CITY_COLUMN_INDEX = 2;
	public static final int PROVINCE_COLUMN_INDEX = 3;
	public static final int POSTAL_CODE_COLUMN_INDEX = 4;

	public CustomerDatabase database;

	private JPanel panel;
	private JScrollPane customerScroller;
	private JTable customers;
	private Input<Client> customersInput;
	private JButton btnAddCustomer;
	private Input<Client> btnAddCustomerInput;
	private JButton btnRemoveCustomer;
	private Input<Client> btnRemoveCustomerInput;
	private JButton btnSaveDatabase;
	private Input<Client> btnSaveDatabaseInput;
	private JButton btnLoadDatabase;
	private Input<Client> btnLoadDatabaseInput;

	/**
	 * Constructor
	 * 
	 * @param databaseFilename
	 *            the relative file name for the database file
	 */
	public Client(String databaseFilename)
	{
		this.database = new CustomerDatabase(databaseFilename, true);
		createWindow();
	}

	/**
	 * Creates the window (JFrame) and the UI
	 */
	private void createWindow()
	{
		getLookAndFeel();

		setTitle("Customer Manager Pro Edition");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		this.panel = new JPanel();
		this.panel.setSize(new Dimension(WIDTH, HEIGHT));
		this.panel.setBackground(Color.DARK_GRAY);
		this.panel.setLayout(null);

		this.customers = new JTable(initTable(), CUSTOMER_DATA_CLASSLIST);
		this.customers.setBounds(100, 130, 550, 100);
		this.customers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.customersInput = new Input<Client>(Client::onTableUpdate, this);
		this.customers.getModel().addTableModelListener(this.customersInput);

		this.customerScroller = new JScrollPane(this.customers);
		this.customerScroller.setBounds(this.customers.getBounds());

		this.btnAddCustomer = new JButton("Add");
		this.btnAddCustomerInput = new Input<Client>(Client::addCustomer, this);
		this.btnAddCustomer.addActionListener(this.btnAddCustomerInput);
		this.btnAddCustomer.setBounds(100, this.customers.getY() + this.customers.getHeight() + 40, 90, 30);

		this.btnRemoveCustomer = new JButton("Remove");
		this.btnRemoveCustomerInput = new Input<Client>(Client::removeCustomer, this);
		this.btnRemoveCustomer.addActionListener(this.btnRemoveCustomerInput);
		this.btnRemoveCustomer.setBounds(this.btnAddCustomer.getX() + this.btnAddCustomer.getWidth() + 20, this.customers.getY() + this.customers.getHeight() + 40, 90, 30);

		this.btnSaveDatabase = new JButton("Save");
		this.btnSaveDatabaseInput = new Input<Client>(Client::saveDatabase, this);
		this.btnSaveDatabase.addActionListener(this.btnSaveDatabaseInput);
		this.btnSaveDatabase.setBounds(this.btnRemoveCustomer.getX() + this.btnRemoveCustomer.getWidth() + 20, this.customers.getY() + this.customers.getHeight() + 40, 90, 30);

		this.btnLoadDatabase = new JButton("Load");
		this.btnLoadDatabaseInput = new Input<Client>(Client::loadDatabase, this);
		this.btnLoadDatabase.addActionListener(this.btnLoadDatabaseInput);
		this.btnLoadDatabase.setBounds(this.btnSaveDatabase.getX() + this.btnSaveDatabase.getWidth() + 20, this.customers.getY() + this.customers.getHeight() + 40, 90, 30);

		this.panel.add(this.btnAddCustomer);
		this.panel.add(this.btnRemoveCustomer);
		this.panel.add(this.btnSaveDatabase);
		this.panel.add(this.btnLoadDatabase);
		this.panel.add(this.customerScroller);

		setContentPane(panel);
		setVisible(true);
	}

	/**
	 * Initializes the JTable with values from the database
	 * 
	 * @return a vector of vectors with the customer info
	 */
	public Vector<Vector<String>> initTable()
	{
		Vector<Customer> vCustomers = this.database.getCustomerVector();
		Vector<Vector<String>> vvTable = new Vector<Vector<String>>();

		for (Customer current : vCustomers)
		{
			if (current.postalcode == null || current.postalcode.getPostalCode() == null ||
				current.postalcode.getPostalCode().equals(NULL_VALUE) ||
				current.name.equals(NULL_VALUE) || current.address.equals(NULL_VALUE) ||
				current.city.equals(NULL_VALUE) || current.province.equals(NULL_VALUE))
			{
				continue;
			}

			Vector<String> vCustomerContents = new Vector<String>();
			vCustomerContents.add(current.name);
			vCustomerContents.add(current.address);
			vCustomerContents.add(current.city);
			vCustomerContents.add(current.province);
			vCustomerContents.add(current.postalcode.getPostalCode());
			vvTable.add(vCustomerContents);
		}

		return vvTable;
	}

	/**
	 * Callback for the JTable model listener
	 * 
	 * @param client
	 *            a valid client instance
	 */
	private static void onTableUpdate(Client client)
	{
		DefaultTableModel model = (DefaultTableModel) client.customers.getModel();
		Vector<Vector<String>> vCustomerData = new Vector<Vector<String>>(model.getRowCount());

		for (int row = 0; row < model.getRowCount(); ++row)
		{
			Vector<String> vRow = new Vector<String>(model.getColumnCount());

			for (int column = 0; column < model.getColumnCount(); ++column)
				vRow.add((String) model.getValueAt(row, column));

			vCustomerData.add(vRow);
		}

		client.database.setCustomerVector(CustomerDatabase.convertCustomerDataToCustomerVector(vCustomerData));
	}

	/**
	 * Callback for the add button
	 * 
	 * @param client
	 *            a valid client instance
	 */
	private static void addCustomer(Client client)
	{
		Customer newCustomer = new Customer(NULL_VALUE, NULL_VALUE, NULL_VALUE, NULL_VALUE, NULL_VALUE);
		client.database.push(newCustomer);
		DefaultTableModel model = (DefaultTableModel) client.customers.getModel();
		model.addRow(new String[] { newCustomer.name, newCustomer.address, newCustomer.city, newCustomer.province, newCustomer.postalcode.getPostalCode() });
		model.fireTableDataChanged();
	}

	/**
	 * Callback for the remove button
	 * 
	 * @param client
	 *            a valid client instance
	 */
	private static void removeCustomer(Client client)
	{
		int iSelected = client.customers.getSelectedRow();

		if (iSelected == -1)
			return;

		client.database.pop((String) client.customers.getValueAt(iSelected, NAME_COLUMN_INDEX));
		DefaultTableModel model = (DefaultTableModel) client.customers.getModel();
		model.removeRow(iSelected);
		model.fireTableDataChanged();
	}

	/**
	 * Callback for the save button
	 * 
	 * @param client
	 *            a valid client instance
	 */
	private static void saveDatabase(Client client)
	{
		// Synchronize any edits before the save
		Vector<Customer> vCustomers = client.database.getCustomerVector();

		for (int i = 0; i < vCustomers.size(); ++i)
		{
			String name = (String) client.customers.getValueAt(i, NAME_COLUMN_INDEX);
			int matchingIndex = 0;

			while (matchingIndex < vCustomers.size())
			{
				if (vCustomers.get(matchingIndex).name.equals(name))
					break;

				++matchingIndex;
			}

			Customer customer = vCustomers.get(matchingIndex);

			customer.name = (String) client.customers.getValueAt(i, NAME_COLUMN_INDEX);
			customer.address = (String) client.customers.getValueAt(i, ADDRESS_COLUMN_INDEX);
			customer.city = (String) client.customers.getValueAt(i, CITY_COLUMN_INDEX);
			customer.province = (String) client.customers.getValueAt(i, PROVINCE_COLUMN_INDEX);
			customer.postalcode = new PostalCode((client.customers.getValueAt(i, POSTAL_CODE_COLUMN_INDEX) != null) ? (String) client.customers.getValueAt(i, POSTAL_CODE_COLUMN_INDEX) : "");

			// if any field is invalid, don't save
			if (customer.postalcode == null || customer.postalcode.getPostalCode() == null ||
					customer.postalcode.getPostalCode().equals(NULL_VALUE) ||
					customer.name.equals(NULL_VALUE) || customer.address.equals(NULL_VALUE) ||
					customer.city.equals(NULL_VALUE) || customer.province.equals(NULL_VALUE))
			{
				vCustomers.remove(i);
				client.database.pop(customer.name);
				continue;
			}

		}
		client.database.save();
	}

	/**
	 * Callback for the load button
	 * 
	 * @param client
	 *            a valid client instance
	 */
	private static void loadDatabase(Client client)
	{
		client.database.load();

		// Discard any unsaved changes after load
		DefaultTableModel model = (DefaultTableModel) client.customers.getModel();
		model.setDataVector(client.initTable(), CUSTOMER_DATA_CLASSLIST);
		model.fireTableDataChanged();
	}

	/**
	 * Sets the "look and feel" of the user interface components to look native
	 * 
	 * @return true if the look and feel is successfully set else false
	 */
	private boolean getLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

public class CustomerListMizM
{
	/**
	 * Static instance of a client
	 */
	public static Client client;

	/**
	 * Main Function (Entry Point)
	 * 
	 * @param args
	 *            command line args (if any)
	 */
	public static void main(String[] args)
	{
		client = new Client("customers.xml");
	}
}