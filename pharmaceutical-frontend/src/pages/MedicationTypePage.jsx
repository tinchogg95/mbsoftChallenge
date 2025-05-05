import React, { useState, useEffect } from 'react';
import { Box, Button, Container, TextField, Typography, Paper, List, ListItem, ListItemText, IconButton } from '@mui/material';
import { Delete, Add } from '@mui/icons-material';
import axios from 'axios';

const MedicationTypePage = () => {
  const [types, setTypes] = useState([]);
  const [newType, setNewType] = useState({ name: '', description: '' });

  useEffect(() => {
    fetchTypes();
  }, []);

  const fetchTypes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/medication-types');
      setTypes(response.data);
    } catch (error) {
      console.error('Error fetching types:', error);
    }
  };

  const handleCreate = async () => {
    try {
      await axios.post('http://localhost:8080/api/medication-types', newType);
      setNewType({ name: '', description: '' });
      fetchTypes();
    } catch (error) {
      console.error('Error creating type:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/medication-types/${id}`);
      fetchTypes();
    } catch (error) {
      console.error('Error deleting type:', error);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Medication Types</Typography>
      
      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h6">Create New Type</Typography>
        <TextField
          label="Name"
          value={newType.name}
          onChange={(e) => setNewType({...newType, name: e.target.value})}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Description"
          value={newType.description}
          onChange={(e) => setNewType({...newType, description: e.target.value})}
          fullWidth
          margin="normal"
        />
        <Button 
          variant="contained" 
          startIcon={<Add />} 
          onClick={handleCreate}
          sx={{ mt: 2 }}
        >
          Create
        </Button>
      </Paper>

      <Paper sx={{ p: 2 }}>
        <Typography variant="h6">All Medication Types</Typography>
        <List>
          {types.map((type) => (
            <ListItem 
              key={type.id}
              secondaryAction={
                <IconButton edge="end" onClick={() => handleDelete(type.id)}>
                  <Delete />
                </IconButton>
              }
            >
              <ListItemText 
                primary={type.name} 
                secondary={type.description} 
              />
            </ListItem>
          ))}
        </List>
      </Paper>
    </Container>
  );
};

export default MedicationTypePage;