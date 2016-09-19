package de.bkroeger.myvisio.view.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.CommandButtonDisplayState;
import org.pushingpixels.flamingo.api.common.JCommandButtonPanel;
import org.pushingpixels.flamingo.api.common.JCommandMenuButton;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.common.icon.EmptyResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.common.popup.JCommandPopupMenu;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntrySecondary;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;

import test.ribbon.BasicCheckRibbon;
import test.svg.transcoded.document_new;
import test.svg.transcoded.document_open;
import test.svg.transcoded.document_print;
import test.svg.transcoded.document_print_preview;
import test.svg.transcoded.document_properties;
import test.svg.transcoded.document_save;
import test.svg.transcoded.document_save_as;
import test.svg.transcoded.edit_clear;
import test.svg.transcoded.edit_copy;
import test.svg.transcoded.edit_find;
import test.svg.transcoded.edit_paste;
import test.svg.transcoded.mail_forward;
import test.svg.transcoded.mail_message_new;
import test.svg.transcoded.network_wireless;
import test.svg.transcoded.printer;
import test.svg.transcoded.system_log_out;
import test.svg.transcoded.text_html;
import test.svg.transcoded.text_x_generic;
import test.svg.transcoded.x_office_document;
import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.view.ApplicationView;

/**
 * <p>
 * Definiert ein Ribbon-Band anstelle eines Menüs.
 * </p><p>
 * Das Band besteht aus folgenden Ribbons:
 * <ul>
 * <li>File - Behandlung von Dokumenten/Dateien; mit den Befehlen
 * <ul>
 * <li>New - Startdialog für neues Dokument anzeigen</li>
 * <li>Open - bestehende Datei öffnen</li>
 * <li>Save - Datei speichern</li>
 * <li>Save_as - Datei speichern</li>
 * </ul></li>
 * <li>Shape - Behandlung von Shapes
 * </li>
 * </ul>
 * </p>
 * 
 * 
 * @author bk
 */
public class MainRibbon {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MainRibbon.class.getName());
	
	// Grösse der Icons im Ribbon
	private static Dimension dim32 = new Dimension(32, 32);
	private static Dimension dim16 = new Dimension(16, 16);
	
	// Referenz auf Hauptansicht, der das Ribbon-Band hinzugefügt wird
	private ApplicationView frame;
	
	// Bundle mit Text-Resourcen
	ResourceBundle bundle = ApplicationModel.getBundle();
	
	// Land/Sprache Lokale
	Locale currLocale = ApplicationModel.getLocale();

	/**
	 * Konstruktor
	 * 
	 * @param frame - Hauptansicht
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainRibbon(ApplicationView frame) {
		this.frame = frame;

		ResizableIcon icon = ImageWrapperResizableIcon.getIcon(getClass()
				.getResource("/resources/32/file.png"), dim32);
		JRibbonBand document_band = new JRibbonBand(bundle.getString("ribbon.document"), null);
		JRibbonBand shape_band = new JRibbonBand(bundle.getString("ribbon.shape"), null);
		
		icon = ImageWrapperResizableIcon.getIcon(getClass()
				.getResource("/resources/16/file_new.png"), dim16);
		JCommandButton file_new_button = new JCommandButton(bundle.getString("command.file.new"), icon);
		
		icon = ImageWrapperResizableIcon.getIcon(getClass()
				.getResource("/resources/16/file_save.png"), dim16);
		JCommandButton file_save_button = new JCommandButton(bundle.getString("command.file.save"), icon);
		
		icon = ImageWrapperResizableIcon.getIcon(getClass()
				.getResource("/resources/16/file_save_as.png"), dim16);
		JCommandButton file_saveas_button = new JCommandButton(bundle.getString("command.file.saveas"), icon);
		
		icon = ImageWrapperResizableIcon.getIcon(getClass()
				.getResource("/resources/16/file_open.png"), dim16);
		JCommandButton file_open_button = new JCommandButton(bundle.getString("command.file.open"), icon);
		
		document_band.addCommandButton(file_new_button, RibbonElementPriority.TOP);
		document_band.addCommandButton(file_save_button, RibbonElementPriority.MEDIUM);
		document_band.addCommandButton(file_saveas_button, RibbonElementPriority.MEDIUM);
		document_band.addCommandButton(file_open_button, RibbonElementPriority.MEDIUM);
		document_band.setResizePolicies((List) Arrays.asList(
				new CoreRibbonResizePolicies.None(document_band.getControlPanel()),
				new IconRibbonBandResizePolicy(document_band.getControlPanel())));
		
		shape_band.setResizePolicies((List) Arrays.asList(
				new IconRibbonBandResizePolicy(document_band.getControlPanel())));
		
		RibbonTask task1 = new RibbonTask("Document", document_band);
		RibbonTask task2 = new RibbonTask("Shape", shape_band);
		
		frame.getRibbon().addTask(task1);
		frame.getRibbon().addTask(task2);
		
		configureApplicationMenu();
		configureTaskBar();
	}

	protected void configureTaskBar() {
		// taskbar components
		JCommandButton taskbarButtonPaste = new JCommandButton("",
				new edit_paste());
		taskbarButtonPaste
				.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		taskbarButtonPaste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Paste activated");
			}
		});
		taskbarButtonPaste.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new SamplePopupMenu();
			}
		});
		taskbarButtonPaste.setActionRichTooltip(new RichTooltip(bundle
				.getString("Paste.text"), bundle
				.getString("Paste.tooltip.actionParagraph1")));
		taskbarButtonPaste.setPopupRichTooltip(new RichTooltip(bundle
				.getString("Paste.text"), bundle
				.getString("Paste.tooltip.popupParagraph1")));
		taskbarButtonPaste.setActionKeyTip("1");
		frame.getRibbon().addTaskbarComponent(taskbarButtonPaste);

		JCommandButton taskbarButtonClear = new JCommandButton("",
				new edit_clear());
		taskbarButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Clear activated");
			}
		});
		taskbarButtonClear.setEnabled(false);
		taskbarButtonClear.setActionKeyTip("2");
		frame.getRibbon().addTaskbarComponent(taskbarButtonClear);

		JCommandButton taskbarButtonCopy = new JCommandButton("",
				new edit_copy());
		taskbarButtonCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Copy activated");
			}
		});
		taskbarButtonCopy.setActionKeyTip("3");
		frame.getRibbon().addTaskbarComponent(taskbarButtonCopy);

		frame.getRibbon().addTaskbarComponent(
				new JSeparator(JSeparator.VERTICAL));

		JCommandButton taskbarButtonFind = new JCommandButton("",
				new edit_find());
		taskbarButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Find activated");
			}
		});
		taskbarButtonFind.setActionKeyTip("4");
		frame.getRibbon().addTaskbarComponent(taskbarButtonFind);
		
//		JLabel label = new JLabel("Title text");
//		frame.getRibbon().addTaskbarComponent(label);
	}
	
	/**
	 * 
	 */
	protected void configureApplicationMenu() {
		
		RibbonApplicationMenuEntryPrimary amEntryNew = new RibbonApplicationMenuEntryPrimary(
				new document_new(),
				bundle.getString("AppMenuNew.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked creating new document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryNew.setActionKeyTip("N");

		RibbonApplicationMenuEntryPrimary amEntryOpen = new RibbonApplicationMenuEntryPrimary(
				new document_open(), bundle
						.getString("AppMenuOpen.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked opening document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryOpen
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {
					@Override
					public void menuEntryActivated(JPanel targetPanel) {
						targetPanel.removeAll();
						JCommandButtonPanel openHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = bundle
								.getString("AppMenuOpen.secondary.textGroupTitle1");
						openHistoryPanel.addButtonGroup(groupName);

						MessageFormat mf = new MessageFormat(bundle
								.getString("AppMenuOpen.secondary.textButton"));
						mf.setLocale(currLocale);
						for (int i = 0; i < 5; i++) {
							JCommandButton historyButton = new JCommandButton(
									mf.format(new Object[] { i }),
									new text_html());
							historyButton
									.setHorizontalAlignment(SwingUtilities.LEFT);
							openHistoryPanel
									.addButtonToLastGroup(historyButton);
						}
						openHistoryPanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});
		amEntryOpen.setActionKeyTip("O");

		RibbonApplicationMenuEntryPrimary amEntrySave = new RibbonApplicationMenuEntryPrimary(
				new document_save(), bundle
						.getString("AppMenuSave.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked saving document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntrySave.setEnabled(false);
		amEntrySave.setActionKeyTip("S");

		RibbonApplicationMenuEntryPrimary amEntrySaveAs = new RibbonApplicationMenuEntryPrimary(
				new document_save_as(), bundle
						.getString("AppMenuSaveAs.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked saving document as");
					}
				}, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		amEntrySaveAs.setActionKeyTip("A");
		amEntrySaveAs.setPopupKeyTip("F");

		RibbonApplicationMenuEntrySecondary amEntrySaveAsWord = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), bundle
						.getString("AppMenuSaveAs.word.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsWord.setDescriptionText(bundle
				.getString("AppMenuSaveAs.word.description"));
		amEntrySaveAsWord.setActionKeyTip("W");
		RibbonApplicationMenuEntrySecondary amEntrySaveAsHtml = new RibbonApplicationMenuEntrySecondary(
				new text_html(), bundle
						.getString("AppMenuSaveAs.html.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsHtml.setDescriptionText(bundle
				.getString("AppMenuSaveAs.html.description"));
		amEntrySaveAsHtml.setEnabled(false);
		amEntrySaveAsHtml.setActionKeyTip("H");
		RibbonApplicationMenuEntrySecondary amEntrySaveAsOtherFormats = new RibbonApplicationMenuEntrySecondary(
				new document_save_as(), bundle
						.getString("AppMenuSaveAs.other.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsOtherFormats.setDescriptionText(bundle
				.getString("AppMenuSaveAs.other.description"));
		amEntrySaveAsOtherFormats.setActionKeyTip("O");

		amEntrySaveAs
				.addSecondaryMenuGroup(bundle
						.getString("AppMenuSaveAs.secondary.textGroupTitle1"),
						amEntrySaveAsWord, amEntrySaveAsHtml,
						amEntrySaveAsOtherFormats);

		RibbonApplicationMenuEntryPrimary amEntryPrint = new RibbonApplicationMenuEntryPrimary(
				new document_print(), bundle
						.getString("AppMenuPrint.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked printing document");
					}
				}, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		amEntryPrint.setActionKeyTip("P");
		amEntryPrint.setPopupKeyTip("W");

		RibbonApplicationMenuEntrySecondary amEntryPrintSelect = new RibbonApplicationMenuEntrySecondary(
				new printer(), bundle
						.getString("AppMenuPrint.print.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintSelect.setDescriptionText(bundle
				.getString("AppMenuPrint.print.description"));
		amEntryPrintSelect.setActionKeyTip("P");
		RibbonApplicationMenuEntrySecondary amEntryPrintDefault = new RibbonApplicationMenuEntrySecondary(
				new document_print(), bundle
						.getString("AppMenuPrint.quick.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintDefault.setDescriptionText(bundle
				.getString("AppMenuPrint.quick.description"));
		amEntryPrintDefault.setActionKeyTip("Q");
		RibbonApplicationMenuEntrySecondary amEntryPrintPreview = new RibbonApplicationMenuEntrySecondary(
				new document_print_preview(), bundle
						.getString("AppMenuPrint.preview.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintPreview.setDescriptionText(bundle
				.getString("AppMenuPrint.preview.description"));
		amEntryPrintPreview.setActionKeyTip("V");

		amEntryPrint.addSecondaryMenuGroup(bundle
				.getString("AppMenuPrint.secondary.textGroupTitle1"),
				amEntryPrintSelect, amEntryPrintDefault, amEntryPrintPreview);

		RibbonApplicationMenuEntrySecondary amEntryPrintMemo = new RibbonApplicationMenuEntrySecondary(
				new text_x_generic(), bundle
						.getString("AppMenuPrint.memo.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintMemo.setActionKeyTip("M");

		amEntryPrint.addSecondaryMenuGroup(bundle
				.getString("AppMenuPrint.secondary.textGroupTitle2"),
				amEntryPrintMemo);

		RibbonApplicationMenuEntryPrimary amEntrySend = new RibbonApplicationMenuEntryPrimary(
				new mail_forward(), bundle
						.getString("AppMenuSend.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked sending document");
					}
				}, CommandButtonKind.POPUP_ONLY);
		amEntrySend.setPopupKeyTip("D");

		RibbonApplicationMenuEntrySecondary amEntrySendMail = new RibbonApplicationMenuEntrySecondary(
				new mail_message_new(), bundle
						.getString("AppMenuSend.email.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendMail.setDescriptionText(bundle
				.getString("AppMenuSend.email.description"));
		amEntrySendMail.setActionKeyTip("E");
		RibbonApplicationMenuEntrySecondary amEntrySendHtml = new RibbonApplicationMenuEntrySecondary(
				new text_html(), bundle
						.getString("AppMenuSend.html.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendHtml.setDescriptionText(bundle
				.getString("AppMenuSend.html.description"));
		amEntrySendHtml.setActionKeyTip("H");
		RibbonApplicationMenuEntrySecondary amEntrySendDoc = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), bundle
						.getString("AppMenuSend.word.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendDoc.setDescriptionText(bundle
				.getString("AppMenuSend.word.description"));
		amEntrySendDoc.setActionKeyTip("W");
		RibbonApplicationMenuEntrySecondary amEntrySendWireless = new RibbonApplicationMenuEntrySecondary(
				new network_wireless(), bundle
						.getString("AppMenuSend.wireless.text"), null,
				CommandButtonKind.POPUP_ONLY);
		amEntrySendWireless.setPopupKeyTip("X");

		amEntrySendWireless.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				JCommandPopupMenu wirelessChoices = new JCommandPopupMenu();

				JCommandMenuButton wiFiMenuButton = new JCommandMenuButton(
						bundle
								.getString("AppMenuSend.wireless.wifi.text"),
						new EmptyResizableIcon(16));
				wiFiMenuButton.setActionKeyTip("W");
				wiFiMenuButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("WiFi activated");
					}
				});
				wirelessChoices.addMenuButton(wiFiMenuButton);

				JCommandMenuButton blueToothMenuButton = new JCommandMenuButton(
						bundle
								.getString("AppMenuSend.wireless.bluetooth.text"),
						new EmptyResizableIcon(16));
				blueToothMenuButton.setActionKeyTip("B");
				blueToothMenuButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("BlueTooth activated");
					}
				});
				wirelessChoices.addMenuButton(blueToothMenuButton);
				return wirelessChoices;
			}
		});

		amEntrySendWireless.setDescriptionText(bundle
				.getString("AppMenuSend.wireless.description"));

		amEntrySend.addSecondaryMenuGroup(bundle
				.getString("AppMenuSend.secondary.textGroupTitle1"),
				amEntrySendMail, amEntrySendHtml, amEntrySendDoc,
				amEntrySendWireless);

		RibbonApplicationMenuEntryPrimary amEntryExit = new RibbonApplicationMenuEntryPrimary(
				new system_log_out(), bundle
						.getString("AppMenuExit.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryExit.setActionKeyTip("X");

		RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
		applicationMenu.addMenuEntry(amEntryNew);
		applicationMenu.addMenuEntry(amEntryOpen);
		applicationMenu.addMenuEntry(amEntrySave);
		applicationMenu.addMenuEntry(amEntrySaveAs);
		applicationMenu.addMenuSeparator();
		applicationMenu.addMenuEntry(amEntryPrint);
		applicationMenu.addMenuEntry(amEntrySend);
		applicationMenu.addMenuSeparator();
		applicationMenu.addMenuEntry(amEntryExit);

		applicationMenu
				.setDefaultCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {
					@Override
					public void menuEntryActivated(JPanel targetPanel) {
						targetPanel.removeAll();
						JCommandButtonPanel openHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = bundle
								.getString("AppMenu.default.textGroupTitle1");
						openHistoryPanel.addButtonGroup(groupName);

						MessageFormat mf = new MessageFormat(bundle
								.getString("AppMenu.default.textButton"));
						mf.setLocale(currLocale);
						for (int i = 0; i < 5; i++) {
							JCommandButton historyButton = new JCommandButton(
									mf.format(new Object[] { i }),
									new text_html());
							historyButton
									.setHorizontalAlignment(SwingUtilities.LEFT);
							openHistoryPanel
									.addButtonToLastGroup(historyButton);
						}
						openHistoryPanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});

		RibbonApplicationMenuEntryFooter amFooterProps = new RibbonApplicationMenuEntryFooter(
				new document_properties(), bundle
						.getString("AppMenuOptions.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked Options");
					}
				});
		RibbonApplicationMenuEntryFooter amFooterExit = new RibbonApplicationMenuEntryFooter(
				new system_log_out(), bundle
						.getString("AppMenuExit.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
		amFooterExit.setEnabled(false);
		applicationMenu.addFooterEntry(amFooterProps);
		applicationMenu.addFooterEntry(amFooterExit);

		frame.getRibbon().setApplicationMenu(applicationMenu);

		RichTooltip appMenuRichTooltip = new RichTooltip();
		appMenuRichTooltip.setTitle(bundle
				.getString("AppMenu.tooltip.title"));
		appMenuRichTooltip.addDescriptionSection(bundle
				.getString("AppMenu.tooltip.paragraph1"));
		try {
			appMenuRichTooltip
					.setMainImage(ImageIO
							.read(BasicCheckRibbon.class
									.getResource("/test/ribbon/appmenubutton-tooltip-main.png")));
			appMenuRichTooltip.setFooterImage(ImageIO
					.read(BasicCheckRibbon.class
							.getResource("/test/ribbon/help-browser.png")));
		} catch (IOException ioe) {
		}
		appMenuRichTooltip.addFooterSection(bundle
				.getString("AppMenu.tooltip.footer1"));
		frame.getRibbon().setApplicationMenuRichTooltip(appMenuRichTooltip);
		frame.getRibbon().setApplicationMenuKeyTip("F");
	}


	private class SamplePopupMenu extends JCommandPopupMenu {
		
 		private static final long serialVersionUID = -2619556147893383911L;

		public SamplePopupMenu() {
			MessageFormat mf = new MessageFormat(bundle
					.getString("TestMenuItem.text"));
			mf.setLocale(currLocale);

			JCommandMenuButton menuButton1 = new JCommandMenuButton(mf
					.format(new Object[] { "1" }), new EmptyResizableIcon(16));
			menuButton1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Test menu item 1 activated");
				}
			});
			menuButton1.setActionKeyTip("1");
			this.addMenuButton(menuButton1);

			JCommandMenuButton menuButton2 = new JCommandMenuButton(mf
					.format(new Object[] { "2" }), new EmptyResizableIcon(16));
			menuButton2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Test menu item 2 activated");
				}
			});
			menuButton2.setActionKeyTip("2");
			this.addMenuButton(menuButton2);

			JCommandMenuButton menuButton3 = new JCommandMenuButton(mf
					.format(new Object[] { "3" }), new EmptyResizableIcon(16));
			menuButton3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Test menu item 3 activated");
				}
			});
			menuButton3.setActionKeyTip("3");
			this.addMenuButton(menuButton3);

			this.addMenuSeparator();

			JCommandMenuButton menuButton4 = new JCommandMenuButton(mf
					.format(new Object[] { "4" }), new EmptyResizableIcon(16));
			menuButton4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Test menu item 4 activated");
				}
			});
			menuButton4.setActionKeyTip("4");
			this.addMenuButton(menuButton4);

			JCommandMenuButton menuButton5 = new JCommandMenuButton(mf
					.format(new Object[] { "5" }), new EmptyResizableIcon(16));
			menuButton5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Test menu item 5 activated");
				}
			});
			menuButton5.setActionKeyTip("5");
			this.addMenuButton(menuButton5);
		}
	}
}
