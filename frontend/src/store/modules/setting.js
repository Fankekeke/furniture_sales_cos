import db from 'utils/localstorage'

export default {
  namespaced: true,
  state: {
    sidebar: {
      opened: true
    },
    settingBar: {
      opened: false
    },
    isMobile: false,
    theme: db.get('THEME', 'light'),
    layout: db.get('LAYOUT', 'side'),
    systemName: '家具销售系统',
    copyright: `${new Date().getFullYear()} <a href="https://berserker287.github.io/" target="_blank">FanK</a>`,
    multipage: getBooleanValue(db.get('MULTIPAGE'), true),
    fixSiderbar: getBooleanValue(db.get('FIX_SIDERBAR'), true),
    fixHeader: getBooleanValue(db.get('FIX_HEADER'), true),
    colorList: [
      'rgb(233, 170, 173)',
      'rgb(226, 172, 154)',
      'rgb(248, 220, 164)',
      'rgb(140, 245, 197)',
      'rgb(181, 221, 161)',
      'rgb(163, 205, 244)',
      'rgb(175, 188, 239)',
      'rgb(179, 153, 217)'
    ],
    color: db.get('COLOR', 'rgb(163, 205, 244)')
  },
  mutations: {
    setDevice (state, isMobile) {
      state.isMobile = isMobile
    },
    setTheme (state, theme) {
      db.save('THEME', theme)
      state.theme = theme
    },
    setLayout (state, layout) {
      db.save('LAYOUT', layout)
      state.layout = layout
    },
    setMultipage (state, multipage) {
      db.save('MULTIPAGE', multipage)
      state.multipage = multipage
    },
    setSidebar (state, type) {
      state.sidebar.opened = type
    },
    fixSiderbar (state, flag) {
      db.save('FIX_SIDERBAR', flag)
      state.fixSiderbar = flag
    },
    fixHeader (state, flag) {
      db.save('FIX_HEADER', flag)
      state.fixHeader = flag
    },
    setSettingBar (state, flag) {
      state.settingBar.opened = flag
    },
    setColor (state, color) {
      db.save('COLOR', color)
      state.color = color
    }
  }
}

function getBooleanValue (value, defaultValue) {
  if (Object.is(value, null)) {
    return defaultValue
  }
  if (JSON.stringify(value) !== '{}') {
    return value
  } else {
    return false
  }
}
