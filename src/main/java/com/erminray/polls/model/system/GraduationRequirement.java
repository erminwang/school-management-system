//package com.erminray.polls.model.system;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.util.Map;
//import java.util.Set;
//
////Obsolete until future use
//@Entity
//@Table(name = "graduationRequirements")
//public class GraduationRequirement {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(
//        mappedBy = "gradua",
//        cascade = CascadeType.ALL,
//        fetch = FetchType.EAGER,
//        //when set choice to null or to another Address object -> disconnecting relationship, this will remove the choice objects
//        orphanRemoval = true
//    )
//    private Set<CourseType> mandatoryCourses;
//
//    @NotBlank
//    private int unitstoGraduation;
//
//    @NotBlank
//    @ElementCollection
//    private Map<BreadthType, Integer> breadthTypeMapUnits;
//
//    @NotBlank
//    private int upperCredits;
//
//    @NotBlank
//    private int lowerCredits;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Set<CourseType> getMandatoryCourses() {
//        return mandatoryCourses;
//    }
//
//    public void setMandatoryCourses(Set<CourseType> mandatoryCourses) {
//        this.mandatoryCourses = mandatoryCourses;
//    }
//
//    public int getUnitstoGraduation() {
//        return unitstoGraduation;
//    }
//
//    public void setUnitstoGraduation(int unitstoGraduation) {
//        this.unitstoGraduation = unitstoGraduation;
//    }
//
//    public Map<BreadthType, Integer> getBreadthTypeMapUnits() {
//        return breadthTypeMapUnits;
//    }
//
//    public void setBreadthTypeMapUnits(Map<BreadthType, Integer> breadthTypeMapUnits) {
//        this.breadthTypeMapUnits = breadthTypeMapUnits;
//    }
//
//    public int getUpperCredits() {
//        return upperCredits;
//    }
//
//    public void setUpperCredits(int upperCredits) {
//        this.upperCredits = upperCredits;
//    }
//
//    public int getLowerCredits() {
//        return lowerCredits;
//    }
//
//    public void setLowerCredits(int lowerCredits) {
//        this.lowerCredits = lowerCredits;
//    }
//
//}
