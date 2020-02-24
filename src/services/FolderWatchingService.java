package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Consumer;

import exceptions.MessageDeletedException;
import javafx.application.Platform;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

public class FolderWatchingService {

	private WatchService watcher;
	private Path messagesPath;
	private boolean kraj = false;

	public FolderWatchingService() {
		super();
		messagesPath = Paths.get("." + File.separator + "messages");
	}

	public void run() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			Files.walk(messagesPath).filter(Files::isDirectory).forEach(new Consumer<Path>() {

				@Override
				public void accept(Path arg0) {
					try {
						arg0.register(watcher, ENTRY_DELETE);
					} catch (IOException e) {

						e.printStackTrace();
					}
					System.out.println("New watching service for: " + arg0.toString());
				}

			});

			while (!kraj) {

				WatchKey key;
				key = watcher.take();
				for (WatchEvent<?> event : key.pollEvents()) {

					WatchEvent.Kind<?> kind = event.kind();
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path newFile = ev.context();

					if (kind == ENTRY_DELETE) {
					
						try {
							throw new MessageDeletedException();
						} catch (MessageDeletedException e) {
							Platform.runLater(() -> {

								e.showMessage(newFile);

							});

						}

					}

				}

				boolean isValid = key.reset();
				if (!isValid)
					kraj = true;

			}
			System.out.println("Shutting down!");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void setEndOfService() {
		this.kraj = true;
	}

}
