package main;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.*;
import javax.swing.*;

	
	public class Graph_M 
	{
		public class Vertex 
		{
			HashMap<String, Integer> nbrs = new HashMap<>();
		}

		static HashMap<String, Vertex> vtces;
		JFrame frame;

		public Graph_M() 
		{
			vtces = new HashMap<>();
			 try {
		            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        SwingUtilities.invokeLater(() -> {
		            frame = new JFrame("Pune Metro Sathi");
		            frame.setSize(600, 400);
		            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		            JPanel panel = new JPanel();
		            panel.setLayout(new GridBagLayout());

		            GridBagConstraints gbc = new GridBagConstraints();
		            gbc.gridx = 0;
		            gbc.gridy = 0;
		            gbc.gridwidth = 2;
		            gbc.insets = new Insets(10, 10, 10, 10);

		            JButton displayStationsBtn = new JButton("Display Stations");
		            displayStationsBtn.setBackground(new Color(0, 153, 204));
		            displayStationsBtn.setForeground(Color.ORANGE);
		            displayStationsBtn.setFont(new Font("Arial", Font.BOLD, 14));
		            displayStationsBtn.setPreferredSize(new Dimension(200, 40));
		            displayStationsBtn.setFocusPainted(false);
		            panel.add(displayStationsBtn, gbc);

		            gbc.gridy++;
		            JLabel sourceLabel = new JLabel("Source Station:");
		            sourceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		            panel.add(sourceLabel, gbc);

		            gbc.gridx = 1;
		            sourceComboBox = new JComboBox<>();
		            sourceComboBox.setPreferredSize(new Dimension(200, 40));
		            panel.add(sourceComboBox, gbc);

		            gbc.gridx = 0;
		            gbc.gridy++;
		            JLabel destinationLabel = new JLabel("Destination Station:");
		            destinationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		            panel.add(destinationLabel, gbc);

		            gbc.gridx = 1;
		            destinationComboBox = new JComboBox<>();
		            destinationComboBox.setPreferredSize(new Dimension(200, 40));
		            panel.add(destinationComboBox, gbc);

		            gbc.gridx = 0;
		            gbc.gridy++;
		            gbc.gridwidth = 2;
		            JButton findPathBtn = new JButton("Find Path");
		            findPathBtn.setBackground(new Color(0, 153, 0));
		            findPathBtn.setForeground(Color.ORANGE);
		            findPathBtn.setFont(new Font("Arial", Font.BOLD, 14));
		            findPathBtn.setPreferredSize(new Dimension(200, 40));
		            findPathBtn.setFocusPainted(false);
		            panel.add(findPathBtn, gbc);
		            
		            gbc.gridx = 0;
		            gbc.gridy++;
		            gbc.gridwidth = 2;
		            JButton findtime = new JButton("Find Time Required");
		            findtime.setBackground(new Color(0, 153, 0));
		            findtime.setForeground(Color.ORANGE);
		            findtime.setFont(new Font("Arial", Font.BOLD, 14));
		            findtime.setPreferredSize(new Dimension(200, 40));
		            findtime.setFocusPainted(false);
		            panel.add(findtime, gbc);
		            
		            gbc.gridx = 0;
		            gbc.gridy++;
		            gbc.gridwidth = 2;
		            JButton shortestD= new JButton("SHORTEST PATH (DISTANCE WISE)");
		            shortestD.setBackground(new Color(0, 153, 0));
		            shortestD.setForeground(Color.ORANGE);
		            shortestD.setFont(new Font("Arial", Font.BOLD, 14));
		            shortestD.setPreferredSize(new Dimension(300, 40));
		            shortestD.setFocusPainted(false);
		            panel.add(shortestD, gbc);
		            
		            gbc.gridx = 0;
		            gbc.gridy++;
		            gbc.gridwidth = 2;
		            JButton shortestT = new JButton("SHORTEST PATH (Time WISE)");
		            shortestT.setBackground(new Color(0, 153, 0));
		            shortestT.setForeground(Color.ORANGE);
		            shortestT.setFont(new Font("Arial", Font.BOLD, 14));
		            shortestT.setPreferredSize(new Dimension(300, 40));
		            shortestT.setFocusPainted(false);
		            panel.add(shortestT, gbc);
		            
		            gbc.gridy++;
		            JTextArea outputArea = new JTextArea(10, 40);
		            outputArea.setEditable(false);
		            outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
		            panel.add(new JScrollPane(outputArea), gbc);

		            frame.getContentPane().add(panel);
		            frame.pack();
		            frame.setLocationRelativeTo(null);
		            frame.setVisible(true);

		            Graph_M metroGraph = this;
		            Graph_M.Create_Metro_Map(metroGraph);

		            displayStationsBtn.addActionListener(e -> {
		                outputArea.setText("");
		                outputArea.append("\n***********************************************************************\n");
		                ArrayList<String> stations = new ArrayList<>(metroGraph.vtces.keySet());
		                sourceComboBox.removeAllItems();
		                destinationComboBox.removeAllItems();
		                for (String station : stations) {
		                    sourceComboBox.addItem(station);
		                    destinationComboBox.addItem(station);
		                }
		                outputArea.append("Stations displayed successfully.\n");
		                outputArea.append("\n***********************************************************************\n");
		            });

		            findPathBtn.addActionListener(e -> {
		                String source = Objects.requireNonNull(sourceComboBox.getSelectedItem()).toString();
		                String destination = Objects.requireNonNull(destinationComboBox.getSelectedItem()).toString();
		                if (!source.isEmpty() && !destination.isEmpty()) {
		                    outputArea.setText("");
		                    int distance = metroGraph.dijkstra(source, destination, false);
		                    if (distance == 0) {
		                        outputArea.append("Invalid input or no path exists between the source and destination.\n");
		                    } else {
		                        outputArea.append("Shortest distance from " + source + " to " + destination + " is " + distance + " KM.\n");
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(frame, "Please select both source and destination stations.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            });
		            
		            findtime.addActionListener(e -> {
		                String source = Objects.requireNonNull(sourceComboBox.getSelectedItem()).toString();
		                String destination = Objects.requireNonNull(destinationComboBox.getSelectedItem()).toString();
		                if (!source.isEmpty() && !destination.isEmpty()) {
		                    outputArea.setText("");
		                    int distance = metroGraph.dijkstra(source, destination, true)/60;
		                    if (distance == 0) {
		                        outputArea.append("Invalid input or no path exists between the source and destination.\n");
		                    } else {
		                        outputArea.append("Shortest Distance from " + source + " to " + destination + " is " + distance + " Minutes\n");
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(frame, "Please select both source and destination stations.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            });

		            shortestD.addActionListener(e -> {
		                String source = Objects.requireNonNull(sourceComboBox.getSelectedItem()).toString();
		                String destination = Objects.requireNonNull(destinationComboBox.getSelectedItem()).toString();
		                
		                HashMap<String, Boolean> processed2 = new HashMap<>();
		                outputArea.setText("");
						if(!metroGraph.containsVertex(source) || !metroGraph.containsVertex(destination) || !metroGraph.hasPath(source, destination, processed2))
							outputArea.append("THE INPUTS ARE INVALID");
						else 
						{
							ArrayList<String> str = metroGraph.get_Interchanges(metroGraph.Get_Minimum_Distance(source, destination));
							int len = str.size();
							
							outputArea.append("SOURCE STATION : " + source);
							outputArea.append("\nSOURCE STATION : " + destination);
							outputArea.append("\nDISTANCE : " + str.get(len-1));
							outputArea.append("\nNUMBER OF INTERCHANGES : " + str.get(len-2));
							outputArea.append("\n~~~~~~~~~~~~~");
							outputArea.append("\nSTART  ==>  " + str.get(0));
							for(int i=1; i<len-3; i++)
							{
								outputArea.append("\n"+str.get(i));
							}
							outputArea.append("\n"+str.get(len-3) + "   ==>    END");
							outputArea.append("\n\n~~~~~~~~~~~~~");
						}
		                
		                
		            });
		            
		            shortestT.addActionListener(e -> {
		                String source = Objects.requireNonNull(sourceComboBox.getSelectedItem()).toString();
		                String destination = Objects.requireNonNull(destinationComboBox.getSelectedItem()).toString();
		                HashMap<String, Boolean> processed3 = new HashMap<>();
		                outputArea.setText("");
						if(!metroGraph.containsVertex(source) || !metroGraph.containsVertex(destination) || !metroGraph.hasPath(source, destination, processed3))
							outputArea.append("\nTHE INPUTS ARE INVALID");
						else
						{
							ArrayList<String> str = metroGraph.get_Interchanges(metroGraph.Get_Minimum_Time(source, destination));
							int len = str.size();
							outputArea.append("\nSOURCE STATION : " + source);
							outputArea.append("\nDESTINATION STATION : " + destination);
							outputArea.append("\nTIME : " + str.get(len-1)+" MINUTES");
							outputArea.append("\nNUMBER OF INTERCHANGES : " + str.get(len-2));
							outputArea.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							outputArea.append("\nSTART  ==>  " + str.get(0) + " ==>  ");
							for(int i=1; i<len-3; i++)
							{
								outputArea.append("\n"+str.get(i));
							}
							outputArea.append("\n"+str.get(len-3) + "   ==>    END");
							outputArea.append("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
		            });
		        });
		}

		public int numVetex() 
		{
			return this.vtces.size();
		}

		public boolean containsVertex(String vname) 
		{
			return this.vtces.containsKey(vname);
		}

		public void addVertex(String vname) 
		{
			Vertex vtx = new Vertex();
			vtces.put(vname, vtx);
		}

		public void removeVertex(String vname) 
		{
			Vertex vtx = vtces.get(vname);
			ArrayList<String> keys = new ArrayList<>(vtx.nbrs.keySet());

			for (String key : keys) 
			{
				Vertex nbrVtx = vtces.get(key);
				nbrVtx.nbrs.remove(vname);
			}

			vtces.remove(vname);
		}

		public int numEdges() 
		{
			ArrayList<String> keys = new ArrayList<>(vtces.keySet());
			int count = 0;

			for (String key : keys) 
			{
				Vertex vtx = vtces.get(key);
				count = count + vtx.nbrs.size();
			}

			return count / 2;
		}

		public boolean containsEdge(String vname1, String vname2) 
		{
			Vertex vtx1 = vtces.get(vname1);
			Vertex vtx2 = vtces.get(vname2);
			
			if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)) {
				return false;
			}

			return true;
		}

		public void addEdge(String vname1, String vname2, int value) 
		{
			Vertex vtx1 = vtces.get(vname1); 
			Vertex vtx2 = vtces.get(vname2); 

			if (vtx1 == null || vtx2 == null || vtx1.nbrs.containsKey(vname2)) {
				return;
			}

			vtx1.nbrs.put(vname2, value);
			vtx2.nbrs.put(vname1, value);
		}

		public void removeEdge(String vname1, String vname2) 
		{
			Vertex vtx1 = vtces.get(vname1);
			Vertex vtx2 = vtces.get(vname2);
			
			//check if the vertices given or the edge between these vertices exist or not
			if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)) {
				return;
			}

			vtx1.nbrs.remove(vname2);
			vtx2.nbrs.remove(vname1);
		}

		public void display_Map() 
		{
			System.out.println("\t Pune Metro Sathi");
			System.out.println("\t------------------");
			System.out.println("----------------------------------------------------\n");
			ArrayList<String> keys = new ArrayList<>(vtces.keySet());

			for (String key : keys) 
			{
				String str = key + " =>\n";
				Vertex vtx = vtces.get(key);
				ArrayList<String> vtxnbrs = new ArrayList<>(vtx.nbrs.keySet());
				
				for (String nbr : vtxnbrs)
				{
					str = str + "\t" + nbr + "\t";
                    			if (nbr.length()<16)
                    			str = str + "\t";
                    			if (nbr.length()<8)
                    			str = str + "\t";
                    			str = str + vtx.nbrs.get(nbr) + "\n";
				}
				System.out.println(str);
			}
			System.out.println("\t------------------");
			System.out.println("---------------------------------------------------\n");

		}
		
		public void display_Stations() 
		{
			System.out.println("\n***********************************************************************\n");
			ArrayList<String> keys = new ArrayList<>(vtces.keySet());
			int i=1;
			for(String key : keys) 
			{
				System.out.println(i + ". " + key);
				i++;
			}
			System.out.println("\n***********************************************************************\n");
		}
			
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> processed) 
		{
			// DIR EDGE
			if (containsEdge(vname1, vname2)) {
				return true;
			}

			//MARK AS DONE
			processed.put(vname1, true);

			Vertex vtx = vtces.get(vname1);
			ArrayList<String> nbrs = new ArrayList<>(vtx.nbrs.keySet());

			//TRAVERSE THE NBRS OF THE VERTEX
			for (String nbr : nbrs) 
			{

				if (!processed.containsKey(nbr))
					if (hasPath(nbr, vname2, processed))
						return true;
			}

			return false;
		}
		
		
		private class DijkstraPair implements Comparable<DijkstraPair> 
		{
			String vname;
			String psf;
			int cost;

			/*
			The compareTo method is defined in Java.lang.Comparable.
			Here, we override the method because the conventional compareTo method
			is used to compare strings,integers and other primitive data types. But
			here in this case, we intend to compare two objects of DijkstraPair class.
			*/ 

			/*
			Removing the overriden method gives us this errror:
			The type Graph_M.DijkstraPair must implement the inherited abstract method Comparable<Graph_M.DijkstraPair>.compareTo(Graph_M.DijkstraPair)

			This is because DijkstraPair is not an abstract class and implements Comparable interface which has an abstract 
			method compareTo. In order to make our class concrete(a class which provides implementation for all its methods)
			we have to override the method compareTo
			 */
			@Override
			public int compareTo(DijkstraPair o) 
			{
				return o.cost - this.cost;
			}
		}
		
		public int dijkstra(String src, String des, boolean nan) 
		{
			int val = 0;
			ArrayList<String> ans = new ArrayList<>();
			HashMap<String, DijkstraPair> map = new HashMap<>();

			Heap<DijkstraPair> heap = new Heap<>();

			for (String key : vtces.keySet()) 
			{
				DijkstraPair np = new DijkstraPair();
				np.vname = key;
				//np.psf = "";
				np.cost = Integer.MAX_VALUE;

				if (key.equals(src)) 
				{
					np.cost = 0;
					np.psf = key;
				}

				heap.add(np);
				map.put(key, np);
			}

			//keep removing the pairs while heap is not empty
			while (!heap.isEmpty()) 
			{
				DijkstraPair rp = heap.remove();
				
				if(rp.vname.equals(des))
				{
					val = rp.cost;
					break;
				}
				
				map.remove(rp.vname);

				ans.add(rp.vname);
				
				Vertex v = vtces.get(rp.vname);
				for (String nbr : v.nbrs.keySet()) 
				{
					if (map.containsKey(nbr)) 
					{
						int oc = map.get(nbr).cost;
						Vertex k = vtces.get(rp.vname);
						int nc;
						if(nan)
							nc = rp.cost + 120 + 40*k.nbrs.get(nbr);
						else
							nc = rp.cost + k.nbrs.get(nbr);

						if (nc < oc) 
						{
							DijkstraPair gp = map.get(nbr);
							gp.psf = rp.psf + nbr;
							gp.cost = nc;

							heap.updatePriority(gp);
						}
					}
				}
			}
			return val;
		}
		
		private class Pair 
		{
			String vname;
			String psf;
			int min_dis;
			int min_time;
		}
		
		public String Get_Minimum_Distance(String src, String dst) 
		{
			int min = Integer.MAX_VALUE;
			//int time = 0;
			String ans = "";
			HashMap<String, Boolean> processed = new HashMap<>();
			LinkedList<Pair> stack = new LinkedList<>();

			// create a new pair
			Pair sp = new Pair();
			sp.vname = src;
			sp.psf = src + "  ";
			sp.min_dis = 0;
			sp.min_time = 0;
			
			// put the new pair in stack
			stack.addFirst(sp);

			// while stack is not empty keep on doing the work
			while (!stack.isEmpty()) 
			{
				// remove a pair from stack
				Pair rp = stack.removeFirst();

				if (processed.containsKey(rp.vname)) 
				{
					continue;
				}

				// processed put
				processed.put(rp.vname, true);
				
				//if there exists a direct edge b/w removed pair and destination vertex
				if (rp.vname.equals(dst)) 
				{
					int temp = rp.min_dis;
					if(temp<min) {
						ans = rp.psf;
						min = temp;
					}
					continue;
				}

				Vertex rpvtx = vtces.get(rp.vname);
				ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());

				for(String nbr : nbrs) 
				{
					// process only unprocessed nbrs
					if (!processed.containsKey(nbr)) {

						// make a new pair of nbr and put in queue
						Pair np = new Pair();
						np.vname = nbr;
						np.psf = rp.psf + nbr + "  ";
						np.min_dis = rp.min_dis + rpvtx.nbrs.get(nbr); 
						//np.min_time = rp.min_time + 120 + 40*rpvtx.nbrs.get(nbr); 
						stack.addFirst(np);
					}
				}
			}
			ans = ans + Integer.toString(min);
			return ans;
		}
		
		
		public String Get_Minimum_Time(String src, String dst) 
		{
			int min = Integer.MAX_VALUE;
			String ans = "";
			HashMap<String, Boolean> processed = new HashMap<>();
			LinkedList<Pair> stack = new LinkedList<>();

			// create a new pair
			Pair sp = new Pair();
			sp.vname = src;
			sp.psf = src + "  ";
			sp.min_dis = 0;
			sp.min_time = 0;
			
			// put the new pair in queue
			stack.addFirst(sp);

			// while queue is not empty keep on doing the work
			while (!stack.isEmpty()) {

				// remove a pair from queue
				Pair rp = stack.removeFirst();

				if (processed.containsKey(rp.vname)) 
				{
					continue;
				}

				// processed put
				processed.put(rp.vname, true);

				//if there exists a direct edge b/w removed pair and destination vertex
				if (rp.vname.equals(dst)) 
				{
					int temp = rp.min_time;
					if(temp<min) {
						ans = rp.psf;
						min = temp;
					}
					continue;
				}

				Vertex rpvtx = vtces.get(rp.vname);
				ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());

				for (String nbr : nbrs) 
				{
					// process only unprocessed nbrs
					if (!processed.containsKey(nbr)) {

						// make a new pair of nbr and put in queue
						Pair np = new Pair();
						np.vname = nbr;
						np.psf = rp.psf + nbr + "  ";
						//np.min_dis = rp.min_dis + rpvtx.nbrs.get(nbr);
						np.min_time = rp.min_time + 120 + 40*rpvtx.nbrs.get(nbr); 
						stack.addFirst(np);
					}
				}
			}
			Double minutes = Math.ceil((double)min / 60);
			ans = ans + Double.toString(minutes);
			return ans;
		}
		
		public ArrayList<String> get_Interchanges(String str)
		{
			ArrayList<String> arr = new ArrayList<>();
			String res[] = str.split("  ");
			arr.add(res[0]);
			int count = 0;
			for(int i=1;i<res.length-1;i++)
			{
				int index = res[i].indexOf('~');
				String s = res[i].substring(index+1);
				
				if(s.length()==2)
				{
					String prev = res[i-1].substring(res[i-1].indexOf('~')+1);
					String next = res[i+1].substring(res[i+1].indexOf('~')+1);
					
					if(prev.equals(next)) 
					{
						arr.add(res[i]);
					}
					else
					{
						arr.add(res[i]+" ==> "+res[i+1]);
						i++;
						count++;
					}
				}
				else
				{
					arr.add(res[i]);
				}
			}
			arr.add(Integer.toString(count));
			arr.add(res[res.length-1]);
			return arr;
		}
		
		public static void Create_Metro_Map(Graph_M g)
		{
			g.addVertex("P C M C");
			g.addVertex("SANT TUKARAM NAGAR");
			g.addVertex("BHOSARI NASHIK PHATA");
			g.addVertex("KASARWADI");
			g.addVertex("PHUGEWADI");
			g.addVertex("DAPODI");
			g.addVertex("BOPODI");
			g.addVertex("SHIVAJI NAGAR");
			g.addVertex("VANAZ");
			g.addVertex("ANAND NAGAR");
			g.addVertex("IDEAL COLONY");
			g.addVertex("NAL STOP");
			g.addVertex("GARWARE COLLEGE");
			g.addVertex("DECCAN GYMKHANA");
			g.addVertex("CHHATRAPATI SAMBHAJI UDYAN");
			g.addVertex("P M C");
			g.addVertex("CIVIL COURT");
			g.addVertex("MANGALWAR PETH");
			g.addVertex("PUNE RAILWAY STATION");
			g.addVertex("RUBY CLINIC");
			g.addVertex("BUND GARDEN");
			g.addVertex("YERAWADA");
			g.addVertex("KALYANI NAGAR");
			g.addVertex("RAMWADI");
			g.addVertex("BUDHWAR PETH");
			g.addVertex("MANDAI");
			g.addVertex("SWARGATE");
			
			g.addEdge("P C M C", "SANT TUKARAM NAGAR", 1);
			g.addEdge("SANT TUKARAM NAGAR", "BHOSARI NASHIK PHATA", 1);
			g.addEdge("BHOSARI NASHIK PHATA", "KASARWADI", 2);
			g.addEdge("KASARWADI", "PHUGEWADI", 2);
			g.addEdge("PHUGEWADI", "DAPODI", 1);
			g.addEdge("DAPODI", "BOPODI", 1);
			g.addEdge("BOPODI", "SHIVAJI NAGAR", 4);
			g.addEdge("SHIVAJI NAGAR", "CIVIL COURT", 1);
			g.addEdge("CIVIL COURT", "BUDHWAR PETH", 1);
			g.addEdge("BUDHWAR PETH", "MANDAI", 1);
			g.addEdge("MANDAI", "SWARGATE", 2);
			g.addEdge("VANAZ", "ANAND NAGAR", 1);
			g.addEdge("ANAND NAGAR", "IDEAL COLONY", 1);
			g.addEdge("IDEAL COLONY", "NAL STOP", 2);
			g.addEdge("NAL STOP", "GARWARE COLLEGE", 2);
			g.addEdge("GARWARE COLLEGE", "DECCAN GYMKHANA", 1);
			g.addEdge("DECCAN GYMKHANA", "CHHATRAPATI SAMBHAJI UDYAN", 1);
			g.addEdge("CHHATRAPATI SAMBHAJI UDYAN", "P M C", 1);
			g.addEdge("P M C", "CIVIL COURT", 1);
			g.addEdge("CIVIL COURT", "MANGALWAR PETH", 1);
			g.addEdge("MANGALWAR PETH", "PUNE RAILWAY STATION", 1);
			g.addEdge("PUNE RAILWAY STATION", "RUBY CLINIC", 1);
			g.addEdge("RUBY CLINIC", "BUND GARDEN", 1);
			g.addEdge("BUND GARDEN", "YERAWADA", 1);
			g.addEdge("YERAWADA", "KALYANI NAGAR", 3);
			g.addEdge("KALYANI NAGAR", "RAMWADI", 2);
		}
		
		public static String[] printCodelist()
		{
			System.out.println("List of station along with their codes:\n");
			ArrayList<String> keys = new ArrayList<>(vtces.keySet());
			int i=1,j=0,m=1;
			StringTokenizer stname;
			String temp="";
			String codes[] = new String[keys.size()];
			char c;
			for(String key : keys) 
			{
				stname = new StringTokenizer(key);
				codes[i-1] = "";
				j=0;
				while (stname.hasMoreTokens())
				{
				        temp = stname.nextToken();
				        c = temp.charAt(0);
				        while (c>47 && c<58)
				        {
				                codes[i-1]+= c;
				                j++;
				                c = temp.charAt(j);
				        }
				        if ((c<48 || c>57) && c<123)
				                codes[i-1]+= c;
				}
				if (codes[i-1].length() < 2)
					codes[i-1]+= Character.toUpperCase(temp.charAt(1));
				            
				System.out.print(i + ". " + key + "\t");
				if (key.length()<(22-m))
                    			System.out.print("\t");
				if (key.length()<(14-m))
                    			System.out.print("\t");
                    		if (key.length()<(6-m))
                    			System.out.print("\t");
                    		System.out.println(codes[i-1]);
				i++;
				if (i == (int)Math.pow(10,m))
				        m++;
			}
			return codes;
		}
		
		 static JComboBox<String> sourceComboBox;
		    static JComboBox<String> destinationComboBox;
		
		public static void main(String[] args) {
	     new Graph_M() ; 
	    }

	}
