package strategy;

public class FileManager implements File {
	private File file;
	
	public FileManager(File file) {
		this.file = file;
	}

	@Override
	public void save() {
		file.save();
	}
}
