---
tags:
  - scene
  - graph
  - node
---
A scene node is a node within a [[Scene Graph]]. Every node has two main components, its behavior and its relationships.

A node has exactly one parent, and any amount of children, both of which are other scene nodes. It can reference both freely at any time. Additionally, the node can also reference the roots of the [[Local Scene Graph]] and the [[Main Scene Graph]]. 

### Relationships

| Name       | Description                                                        |
| ---------- | ------------------------------------------------------------------ |
| parent     | Direct parent of the scene node.                                   |
| children   | Children of the node. All children have this node as their parent. |
| root       | The root of the entire scene graph.                                |
| local root | The root of the scene graph that instantiated the node.            |

Additionally, the scene node can pull any of the nodes bellow it in the tree of a certain type, or any and all parents above it of the same.

### Graph Node Stages

The stages of the graph node are the main way to implement logic for the nodes. They are functions that are called at certain times in the scene graph life-cycle. 

| Name         | Descriptions                                                                                                      |
| ------------ | ----------------------------------------------------------------------------------------------------------------- |
| Pre-start    | Called once after the start stage. Is called first for the parent, then for the children.                         |
| Start        | Called once when the node is added to the scene graph. Is called first for the children, then for the parent.     |
| Pre-process  | Called once per frame right before the process stage. Is called first for the parent, then for the children.      |
| Process      | Called once per frame. Is called first for the parent, then for the children.                                     |
| Post-process | Called once per frame right after the process stage. Is called first for all the children, then for the parent.   |
| Stop         | Called once when the node is removed from the scene graph. Is called first for the parent, then for the children. |
| Post-stop    | Called once after the stop stage. Is called first for the children, then for the parent.                          |

**NB:** In order to prevent unintended errors, no node should be removed from the tree in the middle of any of these stages. As such, removing elements from the tree should be handled after all the process stages have completed. This can be achieved by queuing the nodes that should be removed in a list that is resolved at the end of each frame.
