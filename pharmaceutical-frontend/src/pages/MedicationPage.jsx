import React, { useState, useEffect } from 'react';
import { Box, Button, Container, TextField, Typography, Paper, List, ListItem, ListItemText, Select, MenuItem, FormControl, InputLabel } from '@mui/material';
import { Add } from '@mui/icons-material';
import axios from 'axios';

const MedicationPage = () => {
  const [medications, setMedications] = useState([]);
  const [types, setTypes] = useState([]);
  const [newMed, setNewMed] = useState({
    code: '',
    commercialName: '',
    drugName: '',
    typeId: ''
  });
  const [filterType, setFilterType] = useState('');
  const [filterPrefix, setFilterPrefix] = useState('');

  useEffect(() => {
    fetchTypes();
    fetchMedications();
  }, []);

  const fetchTypes = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/medication-types');
      setTypes(response.data);
    } catch (error) {
      console.error('Error al obtener tipos:', error);
    }
  };

  const fetchMedications = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/medications');
      setMedications(response.data);
    } catch (error) {
      console.error('Error al obtener medicamentos:', error);
    }
  };

  const handleCreate = async () => {
    try {
      const type = types.find(t => t.id == newMed.typeId);
      const medication = {
        code: newMed.code,
        commercialName: newMed.commercialName,
        drugName: newMed.drugName,
        type: type
      };
      await axios.post('http://localhost:8080/api/medications', medication);
      setNewMed({
        code: '',
        commercialName: '',
        drugName: '',
        typeId: ''
      });
      fetchMedications();
    } catch (error) {
      console.error('Error al crear medicamento:', error);
    }
  };

  const handleFilterByType = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/medications/by-type/${filterType}`);
      setMedications(response.data);
    } catch (error) {
      console.error('Error al filtrar por tipo:', error);
    }
  };

  const handleFilterByPrefix = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/medications/by-prefix/${filterPrefix}`);
      setMedications(response.data);
    } catch (error) {
      console.error('Error al filtrar por prefijo:', error);
    }
  };

  const handleResetFilters = () => {
    setFilterType('');
    setFilterPrefix('');
    fetchMedications();
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Medicamentos</Typography>
      
      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h6">Crear Nuevo Medicamento</Typography>
        <TextField
          label="Código"
          value={newMed.code}
          onChange={(e) => setNewMed({...newMed, code: e.target.value})}
          fullWidth
          margin="normal"
          type="number"
        />
        <TextField
          label="Nombre Comercial"
          value={newMed.commercialName}
          onChange={(e) => setNewMed({...newMed, commercialName: e.target.value})}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Nombre de Droga"
          value={newMed.drugName}
          onChange={(e) => setNewMed({...newMed, drugName: e.target.value})}
          fullWidth
          margin="normal"
        />
        <FormControl fullWidth margin="normal">
          <InputLabel>Tipo</InputLabel>
          <Select
            value={newMed.typeId}
            label="Tipo"
            onChange={(e) => setNewMed({...newMed, typeId: e.target.value})}
          >
            {types.map((type) => (
              <MenuItem key={type.id} value={type.id}>{type.name}</MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button 
          variant="contained" 
          startIcon={<Add />} 
          onClick={handleCreate}
          sx={{ mt: 2 }}
        >
          Crear
        </Button>
      </Paper>

      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h6">Filtrar Medicamentos</Typography>
        <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
          <FormControl sx={{ minWidth: 200 }}>
            <InputLabel>Tipo</InputLabel>
            <Select
              value={filterType}
              label="Tipo"
              onChange={(e) => setFilterType(e.target.value)}
            >
              {types.map((type) => (
                <MenuItem key={type.id} value={type.name}>{type.name}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button variant="contained" onClick={handleFilterByType}>Filtrar por Tipo</Button>
        </Box>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <TextField
            label="Prefijo (ej: 'a')"
            value={filterPrefix}
            onChange={(e) => setFilterPrefix(e.target.value)}
            sx={{ width: 200 }}
          />
          <Button variant="contained" onClick={handleFilterByPrefix}>Filtrar por Prefijo</Button>
          <Button variant="outlined" onClick={handleResetFilters}>Resetear Filtros</Button>
        </Box>
      </Paper>

      <Paper sx={{ p: 2 }}>
        <Typography variant="h6">Lista de Medicamentos</Typography>
        <List>
          {medications.map((med) => (
            <ListItem key={med.code}>
              <ListItemText 
                primary={`${med.commercialName} (Código: ${med.code})`} 
                secondary={`Droga: ${med.drugName} - Tipo: ${med.type?.name}`} 
              />
            </ListItem>
          ))}
        </List>
      </Paper>
    </Container>
  );
};

export default MedicationPage;