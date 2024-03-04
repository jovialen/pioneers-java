---
tags:
---
The application is the top level controller of the program. It is responsible for managing the top level assets of the program and synchronizing the subsystems like the [[Scene]]s and [[Renderer]]. 

### Systems

| System        | Description                                                     |
| ------------- | --------------------------------------------------------------- |
| [[Event Bus]] | Global event bus available to the entire application.           |
| [[Scene]]     | Content of the applications world.                              |
| [[Renderer]]  | System for rendering the content of the scene to the [[Window]] |
| [[Window]]    | Window associated with the application.                         |

### Life-cycle

| Stages  | Description                                                                                             |
| ------- | ------------------------------------------------------------------------------------------------------- |
| Start   | Called on the start of the application. Also called when something is first added to the application.   |
| Sync    | Called once at the beginning of each frame. Synchronizes the systems of the application before running. |
| Process | Called once per frame. All updates to application state and systems occurs here.                        |
| Stop    | Called once at the end of the application. Also called when something is removed from the application.  |

#### Example

```java
public void run() {
	start();
	
	while (running) {
		sync();
		process();
	}
	
	stop();
}
```

### Multi-threading

The two most intensive systems in the application are the [[Scene]] and the [[Renderer]]. As such, they should be run on separate threads.

However, since the renderer is dependent on the information from the scene, they must have a synchronization phase where they can transfer data without any race conditions. This is the purpose of the synchronization stage.

```java
void sync() {
	renderThread.wait();
	sceneThread.wait();
	
	renderer.sync(scene);
}

void process() {
	renderThread.queueJob();
	sceneThread.queueJob();
}
```
