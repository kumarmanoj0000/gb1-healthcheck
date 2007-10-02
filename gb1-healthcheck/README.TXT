---------
HealthCheck
---------

. features:
  - food
    > edit complex food
    > delete complex food

  - meals
    > list meals
    > create / edit / delete meal 

  - drugs
    > list drugs
    > create / edit / delete drugs

  - medication
    > list medication
    > create / edit / delete drug input

  - physiological measures
    > create / edit / delete energy level
    > create / edit / delete intestinal status

  - analysis
    > energy level graph
    > intestinal status graph
    > correlation food => energy level
    > correlation food => intestinal status
    > correlation drug => energy level
    > correlation drug => intestinal status

  - administration (visible to administrators on control panel)
    > user management
      >> view user list
      >> view user profile
      >> edit user profile (including roles)

  - localized template-based message builder
    > registration confirmation
    > lost password
      * practice: how to use FreeMarker to generate template-based content

  - improve visual design (CSS)
  - add Ajax whererever possible
  
. technical
  - remove FoodRepository.saveSimpleFood(SimpleFood)
  - use annotations wherever possible
  - migrate unit tests to JUnit 4 (with Spring 2.1)
