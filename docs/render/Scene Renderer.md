---
tags:
  - system
  - scene
  - render
---
The scene renderer is a [[Renderer]] used to render [[Scene Graph]]s. 

### Stages

| Name    | Description                                                                                                                      |
| ------- | -------------------------------------------------------------------------------------------------------------------------------- |
| Sync    | Called once per frame before the processing begins. This stage is for extracting all the required rendering data from the scene. |
| Process | The actual rendering occurs in this stage. This stage should not reference the scene, as it occurs on a separate thread.         |

The synchronization stage should be as simple and quick as possible, optimally only extracting data from the scene. This is because during the synchronization stage both the scene and renderer are blocked from processing. Since these two systems process on separate threads, they should be free to do so as quickly as possible. 

##### Example

```java
List<RenderCamera> cameras;

void sync(SceneNode root) {
	cameras.clear();
	for (CameraNode camera : root.getChildrenOfClass(CameraNode.class)) {
		cameras.add(new RenderCamera(camera));
	}
}

void process() {
	for (RenderCamera camera : cameras) {
		renderCamera(camera);
	}
}
```

**NB:** This example is not optimal, as each frame *N* render cameras are freed and *N* render cameras are created. It is better if the same cameras could be reused.

#### Flow-graph

A flow-graph of the entire renderer can be found [[Renderer Flowgraph.canvas|here]].

#### Render Process

There are two main ways of rendering a scene: Deferred and forward rendering. Deferred rendering is quicker with many lights, however less flexible than the simpler forward rendering. 

Deferred rendering is done first, and then afterwards all remaining forward rendering. This is due to how deferred rendering works, as it needs to be done on a black depth-buffer, while forward rendering is not dependent on the depth-buffer being clear.
