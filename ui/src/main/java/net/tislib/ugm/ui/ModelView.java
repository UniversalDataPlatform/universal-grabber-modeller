package net.tislib.ugm.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import net.tislib.ugm.model.Model;
import net.tislib.ugm.service.ModelService;
import net.tislib.ugm.ui.pages.FrameViewPage;
import net.tislib.ugm.ui.pages.MarkersPage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("model")
@RequiredArgsConstructor
public class ModelView extends VerticalLayout {

	private final ModelService modelService;
	private final FrameViewPage frameViewPage;
	private final MarkersPage markersPage;
	private Button saveButton;
	private Button reloadButton;

	@PostConstruct
	public void init() {

		initUI();
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		Model model = modelService.get("imdb");
		render(model);

		saveButton.addClickListener(event -> {
			modelService.saveModel(model);
			render(model);
		});

		reloadButton.addClickListener(event -> {
			onAttach(attachEvent);
		});
	}

	private void render(Model model) {
		frameViewPage.render(model);
		markersPage.render(model);
	}

	private void initUI() {
		Tab markersTab = new Tab("Markers");
		markersPage.setVisible(false);

		Tab frameViewTab = new Tab("View");
		frameViewPage.setVisible(false);

		Tab tab3 = new Tab("Tab three");
		Div page3 = new Div();
		page3.setText("Page#3");
		page3.setVisible(false);

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(markersTab, markersPage);
		tabsToPages.put(frameViewTab, frameViewPage);
		tabsToPages.put(tab3, page3);
		Tabs tabs = new Tabs(markersTab, frameViewTab, tab3);
		Div pages = new Div(markersPage, frameViewPage, page3);
		Set<Component> pagesShown = Stream.of(markersPage)
				.collect(Collectors.toSet());

		tabs.addSelectedChangeListener(event -> {
			pagesShown.forEach(page -> page.setVisible(false));
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			pagesShown.add(selectedPage);
		});

		markersTab.setSelected(true);
		markersPage.setVisible(true);

		add(tabs);
		add(pages);

		setSizeFull();
		pages.setSizeFull();

		HorizontalLayout actions = new HorizontalLayout();
		add(actions);
		actions.setWidthFull();
		Label space = new Label();
		space.setWidthFull();
		actions.add(space);

		saveButton = new Button("Save");
		reloadButton = new Button("Reload");
		saveButton.setWidth("30px");
		reloadButton.setWidth("30px");
		actions.add(saveButton);
		actions.add(reloadButton);
	}

	@ClientCallable
	public void greet(Object name) {
		System.out.println("Hi, " + name);
	}
}