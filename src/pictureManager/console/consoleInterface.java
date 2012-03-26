package pictureManager.console;

import java.sql.Timestamp;
import java.util.List;
import java.io.*;

import pictureManager.dao.DaoRepository;
import pictureManager.dao.MySqlDaoRepository;
import pictureManager.domain.Event;
import pictureManager.domain.EventLocation;
import pictureManager.domain.Picture;

public class consoleInterface {
	
	protected static DaoRepository daoRepository = new MySqlDaoRepository();

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Timestamp now;
		Event eveObject;
		EventLocation evlObject;
		Picture picObject;

		int optionSelected = -1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				
		while (optionSelected != 99) {
			
			System.out.println("Welcome to the PictureManager App");
			System.out.println(">>> PICTURE <<<");
			System.out.println("1. Add Picture");
			//System.out.println("2. Update Picture");
			System.out.println("3. View Picture by ID");
			System.out.println("4. View Picture by Name");
			System.out.println("5. View Event ALL");
			System.out.println("6. Delete Picture by ID");
			//System.out.println("7. Delete Picture ALL");
			System.out.println(">>> EVENTS <<<");
			System.out.println("11. Create Event");
			//System.out.println("12. Update Event");
			System.out.println("13. View Event by ID");
			System.out.println("14. View Event by Name");
			System.out.println("15. View Event ALL");
			System.out.println("16. Delete Event by ID");
			//System.out.println("17. Delete Event ALL");
			System.out.println("99. Exit PictureManager");
			System.out.println();
			System.out.print("Enter >");

			optionSelected = Integer.parseInt(reader.readLine());
			
			switch (optionSelected) {
			case 1:
				
				System.out.println("Adding a Picture:");
				
				picObject = new Picture();
				System.out.print("Please enter the name of the picture>");
				picObject.setPictureName(reader.readLine());
				
				System.out.print("\nPlease enter the location of the picture>");
				picObject.setPictureLocation(reader.readLine());
				
				System.out.print("\nPlease enter the width of the picture>");
				picObject.setPictureDimensionWidth(Integer.parseInt(reader.readLine()));
				
				System.out.print("\nPlease enter the length of the picture>");
				picObject.setPictureDimensionHeight(Integer.parseInt(reader.readLine()));
				
				System.out.print("\nPlease enter the eventID associated with the picture>");
				picObject.setEventID(Long.parseLong(reader.readLine()));
				
				now = new Timestamp(System.currentTimeMillis());
				picObject.setPictureCreated(now);

				daoRepository.getPictureDao().save(picObject);
				
				break;
			case 2:
				System.out.println("Update a Picture:");
				break;
			case 3:
				System.out.println("View a Picture by ID:");
				
				picObject = new Picture();
				
				System.out.print("Please enter the id of the picture>");				
				picObject = daoRepository.getPictureDao().loadById((Long.parseLong(reader.readLine())));
				System.out.println(picObject.toString());
				
				break;
			case 4:
				System.out.println("View a Picture by Name:");
				picObject = new Picture();
				
				System.out.print("Please enter the name of the picture>");
				picObject = daoRepository.getPictureDao().loadByName((reader.readLine()));
				System.out.println(picObject.toString());
				break;
			case 5:
				System.out.println("View ALL Pictures");
				
				List<Picture> picList = daoRepository.getPictureDao().loadAll();
				for (Picture pic : picList)
					System.out.println(pic.toString());
				
				break;
				
			case 6:
				System.out.println("Delete a Picture by ID:");
				picObject = new Picture();
				
				System.out.print("Please enter the id of the picture>");
				picObject.setId(Long.parseLong(reader.readLine()));
				
				daoRepository.getPictureDao().delete(picObject);
				break;
			case 11:
				System.out.println("Adding an Event:");
				
				evlObject = new EventLocation();
				eveObject = new Event();
				
				System.out.print("Please enter the name of the event>");
				eveObject.setEventName(reader.readLine());
				
				System.out.print("\nPlease enter the street address of the event>");
				evlObject.setStreetAddress(reader.readLine());
				
				System.out.print("\nPlease enter the City of the event>");
				evlObject.setCity(reader.readLine());
				
				System.out.print("\nPlease enter the State of the event>");
				evlObject.setState(reader.readLine());
				
				System.out.print("\nPlease enter the Country of the event>");
				evlObject.setCountry(reader.readLine());
				
				System.out.print("\nPlease enter the Lattitude of the event>");
				evlObject.setLattitude(Double.parseDouble(reader.readLine()));
				
				System.out.print("\nPlease enter the Longitude of the event>");
				evlObject.setLongitude(Double.parseDouble(reader.readLine()));
				
				daoRepository.getEventLocationDao().save(evlObject);
				
				now = new Timestamp(System.currentTimeMillis());
				eveObject.setEventDate(now);
				eveObject.setEventLocationId(evlObject.getId());

				daoRepository.getEventDao().save(eveObject);
				
				break;
			case 12:
				System.out.println("Update an Event:");
				break;
			case 13:
				System.out.println("View a Event by ID:");
				eveObject = new Event();
				
				System.out.print("Please enter the id of the event>");				
				eveObject = daoRepository.getEventDao().loadById((Long.parseLong(reader.readLine())));
				System.out.println(eveObject.toString());
				
				break;
			case 14:
				System.out.println("View a Event by Name:");
				eveObject = new Event();
				
				System.out.print("Please enter the name of the event>");
				eveObject = daoRepository.getEventDao().loadByName((reader.readLine()));
				System.out.println(eveObject.toString());
				break;
			case 15:
				System.out.println("View ALL Events");
				
				List<Event> eventList = daoRepository.getEventDao().loadAll();
				for (Event eve : eventList)
					System.out.println(eve.toString());
				
				break;
			case 16:
				System.out.println("Delete an Event by ID:");

				eveObject = new Event();
				
				System.out.print("Please enter the id of the event>");
				eveObject.setId(Long.parseLong(reader.readLine()));
				
				daoRepository.getEventDao().delete(eveObject);
				
				break;
			
			case 99: 
				System.out.println("Good Bye");
				break;
				
			default:
				System.out.println("I don't get it, what do you want to do???");
				break;
			}
			
		}
		
	}
}
