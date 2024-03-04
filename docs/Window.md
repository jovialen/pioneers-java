---
tags:
  - window
  - render
  - asset
---
The window is an asset which describes a native window on the clients computer. The [[Application]] has exactly one window at all times. 

### Surface

The window is primarily a [[Render Surface]] to which the [[Renderer]] target. The window has two buffers, a front and back buffer, which can both be drawn to by the renderer. The front buffer is always visible to the end-user, whilst the back buffer is actually drawn to. This allows for rendering to the window without any graphical artifacts. 
