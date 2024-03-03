---
tags:
  - graph
  - scene
---
A graph-based approach to structuring a scene system. Every scene is defined by collections of [[Scene Node]]s that each have their own behavior. Additionally, every node have children which are also scene nodes. 

The scene graph has no inherit behavior of its own, but serves only to propagate events through the tree. Any actual behavior is implemented in the scene nodes within the tree.

### Example Scene Class

```java
public static class ExampleScene extends Scene {
	public SceneNode instantiate(SceneNode root) {
		SceneNode playerNode = root.addChild(new PlayerScene());
		playerNode.addChild(new Camera3DNode());
		
		root.addChild(new TerrainScene());
		root.addChild(new WaterScene());
		
		return root;
	}
}
```
