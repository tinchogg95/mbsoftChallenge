import React from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, Button } from '@mui/material';

const Navigation = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Button color="inherit" component={Link} to="/product-codes">
          Product Codes
        </Button>
        <Button color="inherit" component={Link} to="/medications">
          Medications
        </Button>
        <Button color="inherit" component={Link} to="/medication-types">
          Medication Types
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Navigation;