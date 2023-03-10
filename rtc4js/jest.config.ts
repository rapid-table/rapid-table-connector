export default {
 preset: 'ts-jest',
  roots: [
    "<rootDir>/src",
    "<rootDir>/test"
  ],
  testEnvironment: "node",
  transform: {
    "^.+\\.(ts|tsx)$": "ts-jest"
  },
};
