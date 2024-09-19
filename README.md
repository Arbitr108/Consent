## ConsentLab task
Implemented by [Pavel Gorbatiuk, https://premiumIt.club](https://premiumIt.club)

https://github.com/user-attachments/assets/4fa509d5-152e-40ae-9444-c867509c7d0a

_To run the app put the value of `settings_id` into the local.properties (this imitates the env parameters)_
```
settings_id='...';
```
_While implementing the task I tried to show my skills and at the same time to avoid overengineering_

+ During the implementation I kept to the clean, SOLID solutions in code and architecture
+ I decided to keep all UserCentrics inner classes away from domain and data layers as those inner classes are the specific implementations of details
+ To keep the things simple i have incapsulated the UserCentrics banner call logic into the ConsentAdapter
+ The initialization of Usercentrics engine runs in a separate scope with background thread
+ I decided to implement a simple MVI ui pattern (while i could work with any patterns, including MVVM) , different patterns for business logic, for example strategy pattern for the costs adjustment logic, the layers are decoupled so if required to scale it will be easy to move to api/impl gradle modules architecture
+ For the multi threading i have used the kotlin coroutines
+ For the Di i have implemented my own simple solution, to scale to the production level i would suggest koin or dagger (depending on the compile time worries ;-))
+ The code has important comments in the critical parts, so feel free to dive into
+ Structured concurrency friendly usecases
+ Unit tests for the critical estimations logic, usually i keep to ~ 80-90% of test coverage in production

### Things to improve for the better production experience
+ Put the Usercentrics engine initialization into background (as it is mentioned in the documentation) using AndroidViewModel (application context aware)
+ Introduce the database or key value datastore to keep costs and easily syncronize with the backend
+ The costs of the data types should be provided from the backend and then saved to the selected storage
+ Use koin or dagger di frameworks