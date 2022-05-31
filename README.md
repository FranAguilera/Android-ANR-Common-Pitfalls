# Android ANR Common Pitfalls
Basic Android App demoing main causes of ANR



| Basic layout  | ANR triggered 
|--------------|--------------
| ![image](https://user-images.githubusercontent.com/4230063/171075522-e5459650-6a01-4ba4-a1b0-c31e2e4bd5c9.png) | ![image](https://user-images.githubusercontent.com/4230063/171075595-0991d76f-a50e-4690-87cf-2ce8e6845e18.png) 


| Deadlock demo - main held by tid=2  | Deadlock demo - background thread held by tid=1 
|--------------|-------------
|![image](https://user-images.githubusercontent.com/4230063/170209092-f3839b96-ddfd-4616-871c-ae5c0a41e7e6.png) | ![image](https://user-images.githubusercontent.com/4230063/170209397-15a60f89-623c-4b51-9cc8-93aae95ad8d5.png)



