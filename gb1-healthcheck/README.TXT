-----------
HealthCheck
-----------

. features:
  - meals
    > edit meal 

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

. technical
  - Action.input() should return INPUT not SUCCESS
  - UI validation for user registration
  - current approach for user pagination not working: replace
  - use annotations wherever possible
  - migrate unit tests to JUnit 4 (with Spring 2.1)

. pain points
  - repeating @Transactional on all service methods (especially exception lists)
  - many web files: ApplicationMessages.properties, struts.xml, applicationContext-actions, *conversion.properties, *validation.xml
  - duplication in ApplicationMessages.properties
