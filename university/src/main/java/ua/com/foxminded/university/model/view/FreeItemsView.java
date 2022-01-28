package ua.com.foxminded.university.model.view;

import java.util.List;
import java.util.Objects;

import ua.com.foxminded.university.model.FreeItem;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

public class FreeItemsView {

	private final Lecture lecture;				 // reschedule lecture in old option
	private final List<FreeItem> freeItems; 	 // all variants to new position of reschedule lecture
	private FreeItem pointedFreeItem;		     // selected option from List<FreeItem> where reschedule lecture will be have new position

	public FreeItemsView(Lecture lecture, List<FreeItem> freeItems) {
		super();
		this.lecture = lecture;
		this.freeItems = freeItems;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public List<FreeItem> getFreeItems() {
		return freeItems;
	}

	public FreeItem getPointedFreeItem() {
		return pointedFreeItem;
	}

	public void setPointedFreeItem(FreeItem pointedFreeItem) {
		this.pointedFreeItem = pointedFreeItem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(freeItems, lecture, pointedFreeItem);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreeItemsView other = (FreeItemsView) obj;
		return Objects.equals(freeItems, other.freeItems) && Objects.equals(lecture, other.lecture)
				&& Objects.equals(pointedFreeItem, other.pointedFreeItem);
	}

	@Override
	public String toString() {
		return "FreeItemsView [lecture=" + lecture + ", freeItems=" + freeItems + ", pointedFreeItem=" + pointedFreeItem
				+ "]";
	}

}
