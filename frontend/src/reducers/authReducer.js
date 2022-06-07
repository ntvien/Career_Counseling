const types = {
  LOGIN: 'login',
  OPEN: 'open',
  RESTORE_STATE: 'restore_state',
  LOGOUT: 'logout',
};

export const actionCreators = {
  login: ({profile, userType}) => ({
    type: types.LOGIN,
    payload: {profile, userType},
  }),
  open: () => ({
    type: types.OPEN,
  }),
  restoreState: ({profile, userType}) => ({
    type: types.RESTORE_STATE,
    payload: {profile, userType},
  }),
  logout: () => ({
    type: types.LOGOUT,
  }),
};

export const intitialState = {
  profile: null,
  userType: null,
  isOpen: false,
  isLoading: true,
};

export const reducer = (state, action) => {
  switch (action.type) {
    case types.LOGIN:
      return {
        ...state,
        profile: action.payload.profile,
        userType: action.payload.userType,
      };
    case types.OPEN:
      return {...state, isOpen: true};
    case types.RESTORE_STATE:
      return {
        ...state,
        profile: action.payload.profile,
        userType: action.payload.userType,
        isOpen: true,
      };
    case types.LOGOUT:
      return {...state, profile: null, userType: null};
  }
};
