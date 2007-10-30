-----------
HealthCheck
-----------

. features:
  - drugs
    > list drugs
    > create / edit / delete drugs

  - medication
    > list medication
    > create / edit / delete drug input

  - physiological measures
    > create / edit / delete intestinal status

  - analysis
    > energy level graph
    > intestinal status graph
    > correlation food => intestinal status
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
  - use Ajax wherever possible

. technical
  - use annotations wherever possible
  - migrate unit tests to JUnit 4 (extensive support in Spring 2.5)

. pain points
  - repeating @Transactional on all service methods, especially exception lists (solved in Spring 2.5)
  - so many web files: ApplicationMessages.properties, struts.xml, applicationContext-actions, *conversion.properties, *validation.xml
  - duplication in ApplicationMessages.properties (use global namespace)
