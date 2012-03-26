package pictureManager.miner;

import java.sql.Timestamp;
import java.util.*;
import java.io.*;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;



import pictureManager.dao.DaoRepository;
import pictureManager.dao.MySqlDaoRepository;
import pictureManager.domain.Picture;

public final class Miner {

  public static void main(String [] args) throws IOException {

	DaoRepository daoRepository = new MySqlDaoRepository();
	
	//File startingPosition = new File("/ImageLibrary/");
	File startingPosition = new File("/mnt/hgfs/vwadhera On My Mac/Pictures/iPhoto Library/Originals/2011/My_PICT");
    List<File> files = getFileListing(startingPosition);
    int counter = 0;
    Picture picObject = new Picture();
    
    for(File file : files ){
    	
    	if (!file.isDirectory()){
    		if (counter < 10) {
    		if (((file.getAbsolutePath().indexOf("face")) == -1) && ((file.getAbsolutePath().toLowerCase().indexOf("jpg")) != -1)){
    			System.out.println(file.getAbsolutePath());
    			counter++;
    		}
    		}
    		
    		try {

				Directory directory = ImageMetadataReader.readMetadata(file).getDirectory(ExifSubIFDDirectory.class);
				
				List<Tag> tagList = (List<Tag>) directory.getTags();
				for (Tag a : tagList )
					System.out.println("Tag Name" + a.getTagName());
				
	    		picObject.setPictureLocation(file.getParent());
	    		picObject.setPictureName(file.getName());
	    		picObject.setPictureCreated(new Timestamp(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL).getTime()));
	    		picObject.setPictureDimensionWidth(directory.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
	    		picObject.setPictureDimensionHeight(directory.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
	    		
	    		daoRepository.getPictureDao().save(picObject);
	    		
	    		System.out.println(picObject.toString());
	    		
	    		picObject = null;
	    		
			} catch (ImageProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MetadataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		
    	}
    }
    System.out.println("Total Count: " + counter);
  }
  
  static public List<File> getFileListing(File aStartingDir) throws FileNotFoundException {
    validateDirectory(aStartingDir);
    List<File> result = getFileListingNoSort(aStartingDir);
    Collections.sort(result);
    return result;
  }

  static private List<File> getFileListingNoSort(File StartingPosition) throws FileNotFoundException {
    
	List<File> result = new ArrayList<File>();
    
    List<File> filesDirs = Arrays.asList(StartingPosition.listFiles());
    
    for(File file : filesDirs) {
      result.add(file); //always add, even if directory
      if (file.isDirectory()) {
        List<File> nextLevelList = getFileListingNoSort(file);
        result.addAll(nextLevelList);
      }
    }
    return result;
  }

  /**
  * Directory is valid if it exists, does not represent a file, and can be read.
  */
  static private void validateDirectory (File aDirectory) throws FileNotFoundException {
    if (aDirectory == null) {
      throw new IllegalArgumentException("Directory should not be null.");
    }
    if (!aDirectory.exists()) {
      throw new FileNotFoundException("Directory does not exist: " + aDirectory);
    }
    if (!aDirectory.isDirectory()) {
      throw new IllegalArgumentException("Is not a directory: " + aDirectory);
    }
    if (!aDirectory.canRead()) {
      throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
    }
  }
} 
