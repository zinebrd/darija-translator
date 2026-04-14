chrome.runtime.onInstalled.addListener(() => {
    chrome.contextMenus.create({
      id: "translate-to-darija",
      title: "Translate to Darija",
      contexts: ["selection"]
    });
  });
  
  chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.menuItemId === "translate-to-darija") {
      chrome.storage.local.set({ selectedText: info.selectionText }, () => {
        chrome.sidePanel.open({ tabId: tab.id });
      });
    }
  });
  
  chrome.action.onClicked.addListener((tab) => {
    chrome.sidePanel.open({ tabId: tab.id });
  });