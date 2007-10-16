-----------
HealthCheck
-----------

. features:
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
  - use annotations wherever possible
  - migrate unit tests to JUnit 4 (with Spring 2.1)

. pain points
  - lazy initialization exceptions
  - repeating @Transactional on all service methods (especially exception lists)
  - many web files: AppMessages, struts.xml, appContext-actions, *conversion, *validation
  - duplication in ApplicationMessages.properties
