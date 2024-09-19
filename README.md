## ConsentLab task
Implemented by Pavel Gorbatiuk
[https://premiumIt.club]

https://github.com/user-attachments/assets/4fa509d5-152e-40ae-9444-c867509c7d0a

### To run the app put the value of `settings_id` into the local.properties (this imitates the env parameters)
### While implementing the task I tried to show my skills and at the same time to avoid overengineering
### I like to present the plan how to solve problems with team and showcase the schemas if required

+ During the implementation I stick to the clean code, SOLID solution in code and architecture
+ I could implement simple MVI ui pattern, different patterns for business logic, for example strategy pattern for the costs adjustment logic, the layers are decoupled so if required to scale it will be easy to move to api/impl gradle modules architecture
+ For the multi threading i have used the kotlin coroutines
+ For the Di i have implemented my own simple solution, to scale to the production level i would suggest koin or dagger (depending on the compile time worries ;-))
+ The code has important comments in the critical parts, so feel free to dive into
+ Structured concurrency friendly usecases
+ Unit tests for the critical estimations logic, usually i keep to ~ 80-90% of test coverage in production

### Things to improve for the better production experience
1. Put the Usercentrics engine initialization into background (as it is mentioned in the documentation) using AndroidViewModel (application context aware)
2. Introduce the database or key value datastore to keep costs and easily syncronize with the backend

3. The costs of the data types should be provided from the backend and then saved to the selected storage
4. Use koin or dagger di frameworks

### Things to improve the engine
1. Usercetrics service.dataCollectedList is expected to return only granted consents so it will be more developer friendly

