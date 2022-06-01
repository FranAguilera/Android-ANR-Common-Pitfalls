# Android ANR Common Pitfalls
Basic Android App demoing main causes of ANR and how to utilize ApplicationExitInfo

To reproduce an ANR:

1) Click on any button
2) Try to interact with screen - Back Button is a good one
3) Wait for ANR dialog to be shown. 
4) ANR stacktraces will be shown on next app launch


| Basic layout  | ANR triggered 
|--------------|--------------
| ![remove_task_1](https://user-images.githubusercontent.com/4230063/171275005-98604cfd-75e5-4937-a542-e555fbe72ca4.png) | ![anr_at_rx_blocking](https://user-images.githubusercontent.com/4230063/171275002-f3935967-bbe5-48b7-91a2-548ccd926578.png)


| Deadlock demo - main held by tid=2  | Deadlock demo - background thread held by tid=1 
|--------------|-------------
| ![anr_deadlock](https://user-images.githubusercontent.com/4230063/171274997-9e898fb2-0b40-43bb-ac0a-f3cd2584d8eb.png) | ![held_by_thread_1](https://user-images.githubusercontent.com/4230063/171274987-7a66128b-b5b8-4e72-8334-2ac036b78eff.png)



