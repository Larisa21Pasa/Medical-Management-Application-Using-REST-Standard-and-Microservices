import * as React from 'react';

export default class WelcomeContent extends React.Component {
  render() {
    return (
      <div style={styles.container}>
        <h2 style={styles.h2}>Welcome to our medical center</h2>
        <h3 style={styles.h3}>Login or Register to see your account details</h3>
      </div>
    );
  }
}

const styles = {
  container: {
    textAlign: 'center',
    backgroundColor: '#f5f5f5', 
    padding: '20px',
    borderRadius: '8px', 
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', 
    maxWidth: '400px',
    margin: 'auto', 
  },
  h2: {
    fontSize: '1.5em',
    marginBottom: '10px',
  },
  h3: {
    fontSize: '1em', 
  },
};
